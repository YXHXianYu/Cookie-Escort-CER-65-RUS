/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 *
 * YXH_XianYu's notes:
 *  - refer to: https://stackoverflow.com/questions/15749192/how-do-i-load-a-file-from-resource-folder
 *  - code from: https://web.archive.org/web/20171212030426mp_/http://grepcode.com/file/repo1.maven.org/maven2/com.opensymphony/xwork/2.1.3/com/opensymphony/xwork2/util/ClassLoaderUtil.java#ClassLoaderUtil
 */
package tool;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class ClassLoaderUtil {
    public static Iterator<URL> getResources(String resourceName, Class callingClass, boolean aggregate) throws IOException {

        AggregateIterator<URL> iterator = new AggregateIterator<URL>();

        iterator.addEnumeration(Thread.currentThread().getContextClassLoader().getResources(resourceName));

        if (!iterator.hasNext() || aggregate) {
            iterator.addEnumeration(ClassLoaderUtil.class.getClassLoader().getResources(resourceName));
        }

        if (!iterator.hasNext() || aggregate) {
            ClassLoader cl = callingClass.getClassLoader();

            if (cl != null) {
                iterator.addEnumeration(cl.getResources(resourceName));
            }
        }

        if (!iterator.hasNext() && (resourceName != null) && ((resourceName.length() == 0) || (resourceName.charAt(0) != '/'))) {
            return getResources('/' + resourceName, callingClass, aggregate);
        }

        return iterator;
    }

    public static URL getResource(String resourceName, Class callingClass) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);

        if (url == null) {
            url = ClassLoaderUtil.class.getClassLoader().getResource(resourceName);
        }

        if (url == null) {
            ClassLoader cl = callingClass.getClassLoader();

            if (cl != null) {
                url = cl.getResource(resourceName);
            }
        }

        if ((url == null) && (resourceName != null) && ((resourceName.length() == 0) || (resourceName.charAt(0) != '/'))) {
            return getResource('/' + resourceName, callingClass);
        }

        return url;
    }

    public static InputStream getResourceAsStream(String resourceName, Class callingClass) {
        URL url = getResource(resourceName, callingClass);

        try {
            return (url != null) ? url.openStream() : null;
        } catch (IOException e) {
            return null;
        }
    }

    public static Class loadClass(String className, Class callingClass) throws ClassNotFoundException {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException ex) {
                try {
                    return ClassLoaderUtil.class.getClassLoader().loadClass(className);
                } catch (ClassNotFoundException exc) {
                    return callingClass.getClassLoader().loadClass(className);
                }
            }
        }
    }

    static class AggregateIterator<E> implements Iterator<E> {

        LinkedList<Enumeration<E>> enums = new LinkedList<Enumeration<E>>();
        Enumeration<E> cur = null;
        E next = null;
        Set<E> loaded = new HashSet<E>();

        public AggregateIterator<E> addEnumeration(Enumeration<E> e) {
            if (e.hasMoreElements()) {
                if (cur == null) {
                    cur = e;
                    next = e.nextElement();
                    loaded.add(next);
                } else {
                    enums.add(e);
                }
            }
            return this;
        }

        public boolean hasNext() {
            return (next != null);
        }

        public E next() {
            if (next != null) {
                E prev = next;
                next = loadNext();
                return prev;
            } else {
                throw new NoSuchElementException();
            }
        }

        private Enumeration<E> determineCurrentEnumeration() {
            if (cur != null && !cur.hasMoreElements()) {
                if (enums.size() > 0) {
                    cur = enums.removeLast();
                } else {
                    cur = null;
                }
            }
            return cur;
        }

        private E loadNext() {
            if (determineCurrentEnumeration() != null) {
                E tmp = cur.nextElement();
                int loadedSize = loaded.size();
                while (loaded.contains(tmp)) {
                    tmp = loadNext();
                    if (tmp == null || loaded.size() > loadedSize) {
                        break;
                    }
                }
                if (tmp != null) {
                    loaded.add(tmp);
                }
                return tmp;
            }
            return null;

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
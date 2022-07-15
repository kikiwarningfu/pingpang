package com.zhihu.matisse.internal;

public class VideoBean {

    /**
     * duration : 91652
     * id : 219407
     * mimeType : video/mp4
     * size : 13271807
     * uri : {"authority":{"decoded":"media","encoded":"media"},"fragment":{},"path":{"pathSegments":["external","video","media","219407"],"decoded":"NOT CACHED","encoded":"/external/video/media/219407"},"query":{},"scheme":"content","uriString":"content://media/external/video/media/219407","host":"NOT CACHED","port":-2}
     */

    private int duration;
    private int id;
    private String mimeType;
    private int size;
    private UriBean uri;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public UriBean getUri() {
        return uri;
    }

    public void setUri(UriBean uri) {
        this.uri = uri;
    }

    public static class UriBean {
        /**
         * authority : {"decoded":"media","encoded":"media"}
         * fragment : {}
         * path : {"pathSegments":["external","video","media","219407"],"decoded":"NOT CACHED","encoded":"/external/video/media/219407"}
         * query : {}
         * scheme : content
         * uriString : content://media/external/video/media/219407
         * host : NOT CACHED
         * port : -2
         */

        private AuthorityBean authority;
        private FragmentBean fragment;
        private PathBean path;
        private QueryBean query;
        private String scheme;
        private String uriString;
        private String host;
        private int port;

        public AuthorityBean getAuthority() {
            return authority;
        }

        public void setAuthority(AuthorityBean authority) {
            this.authority = authority;
        }

        public FragmentBean getFragment() {
            return fragment;
        }

        public void setFragment(FragmentBean fragment) {
            this.fragment = fragment;
        }

        public PathBean getPath() {
            return path;
        }

        public void setPath(PathBean path) {
            this.path = path;
        }

        public QueryBean getQuery() {
            return query;
        }

        public void setQuery(QueryBean query) {
            this.query = query;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public String getUriString() {
            return uriString;
        }

        public void setUriString(String uriString) {
            this.uriString = uriString;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public static class AuthorityBean {
        }

        public static class FragmentBean {
        }

        public static class PathBean {
        }

        public static class QueryBean {
        }
    }
}

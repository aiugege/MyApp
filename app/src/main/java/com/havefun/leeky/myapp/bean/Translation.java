package com.havefun.leeky.myapp.bean;

public class Translation {
    private int status;

    private content content;
    public static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;

        public String getFrom() {
            return from == null ? "" : from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to == null ? "" : to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getVendor() {
            return vendor == null ? "" : vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getOut() {
            return out == null ? "" : out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public int getErrNo() {
            return errNo;
        }

        public void setErrNo(int errNo) {
            this.errNo = errNo;
        }
    }

    //定义 输出返回数据 的方法
    public void show() {
        System.out.println(status);

        System.out.println(content.from);
        System.out.println(content.to);
        System.out.println(content.vendor);
        System.out.println(content.out);
        System.out.println(content.errNo);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Translation.content getContent() {
        return content;
    }

    public void setContent(Translation.content content) {
        this.content = content;
    }
}

package heyong.intellectPinPang.bean.emotion;

public class PictureList {

    /**
     * filePixels : {"fileUrl":"http://images.xlttsports.com/android_img_20210726111056249"}
     */

    private FilePixelsBean filePixels;

    public FilePixelsBean getFilePixels() {
        return filePixels;
    }

    public void setFilePixels(FilePixelsBean filePixels) {
        this.filePixels = filePixels;
    }

    public static class FilePixelsBean {
        /**
         * fileUrl : http://images.xlttsports.com/android_img_20210726111056249
         */

        private String fileUrl;

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}

package heyong.intellectPinPang.utils;


import androidx.recyclerview.widget.RecyclerView;

public class FileUtil {
    private static final String FOLDER_SEPARATOR = "/";

    /**
     * 根据路径 获取文件的名称
     */
    static public String getFilename(String path) {
        if (path == null) {
            return null;
        }
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1)
                : path);
    }


}
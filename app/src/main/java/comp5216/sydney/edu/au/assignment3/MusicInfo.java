package comp5216.sydney.edu.au.assignment3;

import android.net.Uri;

import java.io.Serializable;

public class MusicInfo implements Serializable {

    private  int _id = - 1;       // 音乐标识码
    private  int durationMin = -1 ;   // Music min duration
    private  int durationSec = -1 ;   // Music sec duration
    private String musicName= null ; //音乐名字
    private int size  ;   //音乐文件的大小  返回byte大小
    private String data ;  //获取文件的完整路径
    private Uri uri;


    public void setData(String data) {
        this.data = data;
        this.uri = Uri.parse(data);
    }

    public Uri getUri() {
        return uri;
    }

    public String getData() {
        return data ;
    }

    public void setSize(int size) {
        this.size = size ;
    }

    public double getSize() {
        double mSize = this.size * 1.0 / 1024.0 / 1024.0;
        mSize = Math.round(mSize * 100) / 100.0;
        return mSize ;
    }


    public int get_id() {
        return _id ;
    }


    public String getMusicName() {
        return musicName ;
    }

    public void set_id(int _id) {
        this._id = _id ;
    }

    public void setDuration(int duration) {
        this.durationMin = (int) ((duration / 1000.0) / 60);
        this.durationSec = (int)((duration / 1000.0 / 60.0 - durationMin) * 60);
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName ;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String toString() {
        if (durationSec >= 10) {
            return (musicName + "\r\n" + durationMin + ":" + durationSec
                    + "       "
                    + getSize() + "mb");
        }
        else
            return (musicName + "\r\n" + durationMin + ":0" + durationSec
                    + "       "
                    + getSize() + "mb");
    }

}

package com.mundane.androidtechniqueapply.api.bean;

/**
 * Created by mundane on 2017/3/10 15:01
 */

public class ZhuangBiEntity {

	public String channel;
	public String created_at;
	public String description;
	public String disk;
	public String file_size;
	public int height;
	public int hotpoint;
	public int id;
	public String image_url;
	public String path;
	public String permitted_at;
	public int size;
	public String updated_at;
	public Upload upload;
	public int upload_id;
	public int user_id;
	public int width;

	public static class Upload {
		public String created_at;
		public String description;
		public String disk;
		public int id;
		public String path;
		public int size;
		public String updated_at;
		public String url;
		public int user_id;
	}
}

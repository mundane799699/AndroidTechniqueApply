package com.mundane.androidtechniqueapply.api.bean;

import java.util.List;

/**
 * Created by mundane on 2017/3/7 9:37
 */

public class MovieEntity {

	public int count;
	public int start;
	public String title;
	public int total;
	public List<Subject> subjects;

	public static class Subject {
		public String alt;
		public int collect_count;
		public String id;
		public Image images;
		public String original_title;
		public Rating rating;
		public String subtype;
		public String title;
		public String year;
		public List<Cast> casts;
		public List<Director> directors;
		public List<String> genres;

		public static class Image {
			public String large;
			public String medium;
			public String small;
		}

		public static class Rating {
			public double average;
			public int max;
			public int min;
			public String stars;
		}

		public static class Cast {
			public String alt;
			public Avatars avatars;
			public String id;
			public String name;

			public static class Avatars {
				public String large;
				public String medium;
				public String small;
			}
		}

		public static class Director {
			public String alt;
			public Avatar avatars;
			public String id;
			public String name;

			public static class Avatar {
				public String large;
				public String medium;
				public String small;
			}
		}
	}
}

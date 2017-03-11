package com.mundane.androidtechniqueapply.api.bean;

import java.util.List;

/**
 * Created by mundane on 2017/3/7 12:06
 */

public class Subject {
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

	@Override
	public String toString() {
		return "Subject{" +
				"alt='" + alt + '\'' +
				", collect_count=" + collect_count +
				", id='" + id + '\'' +
				", images=" + images +
				", original_title='" + original_title + '\'' +
				", rating=" + rating +
				", subtype='" + subtype + '\'' +
				", title='" + title + '\'' +
				", year='" + year + '\'' +
				", casts=" + casts +
				", directors=" + directors +
				", genres=" + genres +
				'}';
	}

	public static class Image {
		public String large;
		public String medium;
		public String small;

		@Override
		public String toString() {
			return "Image{" +
					"large='" + large + '\'' +
					", medium='" + medium + '\'' +
					", small='" + small + '\'' +
					'}';
		}
	}

	public static class Rating {
		public double average;
		public int max;
		public int min;
		public String stars;

		@Override
		public String toString() {
			return "Rating{" +
					"average=" + average +
					", max=" + max +
					", min=" + min +
					", stars='" + stars + '\'' +
					'}';
		}
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

			@Override
			public String toString() {
				return "Avatars{" +
						"large='" + large + '\'' +
						", medium='" + medium + '\'' +
						", small='" + small + '\'' +
						'}';
			}
		}

		@Override
		public String toString() {
			return "Cast{" +
					"alt='" + alt + '\'' +
					", avatars=" + avatars +
					", id='" + id + '\'' +
					", name='" + name + '\'' +
					'}';
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

		@Override
		public String toString() {
			return "Director{" +
					"alt='" + alt + '\'' +
					", avatars=" + avatars +
					", id='" + id + '\'' +
					", name='" + name + '\'' +
					'}';
		}
	}
}

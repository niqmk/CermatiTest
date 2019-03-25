package com.test.cermati.model;

import com.google.gson.annotations.SerializedName;

public class ItemModel {
	@SerializedName("name")
	private String name;

	@SerializedName("owner")
	private Owner owner;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public static class Owner {
		@SerializedName("avatar_url")
		private String avatar_url;

		public String getAvatar_url() {
			return avatar_url;
		}

		public void setAvatar_url(String avatar_url) {
			this.avatar_url = avatar_url;
		}
	}
}
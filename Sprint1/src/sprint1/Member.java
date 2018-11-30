//Likes the post passed in as an argument and gives 5 points to the poster
	private void likePost(Post post) {
		if (this.getGroups().contains(post.getGroup()) && !alreadyLikedPost(post) ) {//check if member is in the group in which "post" was posted
			post.addLike(new Like(this));
			post.getMembership().addPoints(5);
		}
	}	
	
	//Checks if this member already likes the post
	private boolean alreadyLikedPost(Post post) {
		for (Like like: post.likes) {
			if (like.getUpVoter() == this) {
				return true;
			}
		}
		return false;
	}
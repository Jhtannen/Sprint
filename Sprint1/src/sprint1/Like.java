package sprint1;

import java.io.Serializable;

public class Like implements Serializable{
	private Member upVoter;
	
	public Like(Member upVoter) {
		this.upVoter = upVoter;
	}
	
	
	public Member getUpVoter() {
		return upVoter;
	}
}

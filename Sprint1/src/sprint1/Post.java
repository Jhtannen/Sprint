package sprint1;
import java.time.LocalDateTime;


abstract class Post {

    // initialize variables
    protected String text;
    protected LocalDateTime date;

    protected Membership membership;

    // constructor to set initial values
    public Post(String txt, LocalDateTime date) {
        text = txt;
        this.date = date;
    }

    // returns value of text
    public String getText() {

        return text;

    }

    // returns local date value
    public LocalDateTime getDate() {

        return date;

    }

    // sets/resets value in text
    public void setText(String text) {

        this.text = text;

    }

    // returns author name
    public Member getAuthor() {

        return membership.getMember();

    }

    // returns group name
    public Group getGroup() {

        return membership.getGroup();

    }

    // sets membership status
    protected void setMembership(Membership originalMembership) {

        this.membership = originalMembership;

    }

    // returns membership status
    protected Membership getMembership() {

        return membership;

    }

}
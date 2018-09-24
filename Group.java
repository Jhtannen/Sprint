import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group {
    private Date dateCreated;
    private String title;
    private String description;
    private List<Answer> answers;
    private List<Membership> memberships = new ArrayList<>();
    private List<Question> questions;

    public Group(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.dateCreated = date;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getTitle() {
        return title;
    }

    // Returns this groups description
    public String getDescription() {
        return description;
    }

    public int getNumberOfMembers() {
        return memberships.size();
    }

    // Returns the Member of this group that corresponds to the emailAddress
    public Member getMember(String emailAddress) {

        for (Membership membership : memberships) {
            if (membership.member.getEmailAddress().equals(emailAddress)) {
                return membership.member;
            }
        }
        Date date = new Date();
        return new Member("Dummy", "Dummy", "NoScreenName", "NoEmail", date);
    }

    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        for (Membership membership : memberships) {
            members.add(membership.member);
        }
        return members;
    }

    // Returns all questions that have been asked in this group in the order
    // that they were asked
    public List<Question> getQuestions() {
        return questions;
    }

    // Returns all answers to all questions that have been asked in this group in
    // the order
    // that they were answered
    public List<Answer> getAnswers() {
        return answers;
    }

    // Provides useful information about this group, neatly formatted
    public String toString() {
        return "Group: " + this.title;
    }
}
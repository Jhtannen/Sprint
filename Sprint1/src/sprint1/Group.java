package sprint1;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Group {
    private LocalDateTime dateCreated;
    private String title;
    private String description;
    private List<Answer> answers;
    private List<Membership> memberships = new ArrayList<>();
    private List<Question> questions;

    Group(String title, String description, LocalDateTime date) {
        this.title = title;
        this.description = description;
        this.dateCreated = date;
    }

    LocalDateTime getDateCreated() {
        return dateCreated;
    }

    String getTitle() {
        return title;
    }

    // Returns this groups description
    String getDescription() {
        return description;
    }

    int getNumOfMembers() {
        return memberships.size();
    }

    // Returns the Member of this group that corresponds to the emailAddress
    Member getMember(String emailAddress) {
        for (Membership membership : memberships) {
            if (membership.getMember().getEmailAddress().equals(emailAddress)) {
                return membership.getMember();
            }
        }
        
        return null;
    }

    void addMembership(Membership membership) {
        memberships.add(membership);
    }

    List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        for (Membership membership : memberships) {
            members.add(membership.getMember());
        }
        return members;
    }

    // Returns all questions that have been asked in this group in the order
    // that they were asked
    List<Question> getQuestions() {
        return questions;
    }

    // Returns all answers to all questions that have been asked in this group in
    // the order
    // that they were answered
    List<Answer> getAnswers() {
        return answers;
    }
    
   
    

    // Provides useful information about this group, neatly formatted
    public String toString() {
        return "Group: " + this.title;
    }
    
}
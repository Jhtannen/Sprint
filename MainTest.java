import java.util.List;
import java.util.Date;
import java.util.ArrayList;

public class MainTest {

    public static void main(String[] args) {

        Date date = new Date();

        Member matt = new Member("Matthew", "Ritter", "Matt", "mwritter@valdosta.edu", date);
        Member virginia = new Member("Virginia", "Ritter", "Gin", "vrharnevious@valdosta.edu", date);

        Group group1 = new Group("First Group", "This is an awesome Group1", date);
        Group group2 = new Group("Second Group", "This is an awesome Group2", date);

        matt.joinGroup(group1, date);
        matt.joinGroup(group2, date);
        virginia.joinGroup(group2, date);

        Question question1 = new Question("First Question Title", "Is this a question?", date);
        Question question2 = new Question("Second Question Title", "Do you know?", date);

        matt.addQuestion(group1, question1, date);
        matt.addQuestion(group1, question2, date);
        matt.addQuestion(group2, question2, date);

        System.out.println("============");
        System.out.println("getMember(mwritter@valdosta.edu):" + group1.getMember("mwritter@valdosta.edu"));
        System.out.println("============");

        for (Group group : matt.getGroups()) {
            System.out.println(matt.getScreenName() + " Joined: " + group.getTitle() + " " + matt.getDateJoined(group)
                    + "\n" + "============");
        }
        for (Group group : virginia.getGroups()) {
            System.out.println(virginia.getScreenName() + " Joined: " + group.getTitle() + " "
                    + virginia.getDateJoined(group) + "\n" + "============\n");
        }
        System.out.println("============");
        System.out.println("getMembers(group2):" + group2.getMembers());
        System.out.println("============");
        Answer answer1 = new Answer(question1, "This is the answer", date);

        matt.addAnswer(group1, question1, answer1, date);
        System.out.println("============");
        System.out.println("GetQuestions For Matt: \n" + matt.getQuestions(group1));
        System.out.println("============");
        System.out.println("GetAnswers For Matt: " + matt.getAnswers(group1));
        System.out.println("============");
        System.out.println("GetAuthor of answer1" + answer1.getAuthor());
        System.out.println("============");
        System.out.println("Member toString method: " + matt.toString());
        System.out.println("============");
        System.out.println("No errors");

    }

}
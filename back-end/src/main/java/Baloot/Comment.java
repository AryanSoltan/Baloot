package Baloot;


import jakarta.persistence.*;
import java.util.*;
@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int commentId;

    @Transient
    private String userEmail;

    @Transient
    private int commodityId;
    private String text;
    private String date;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id")
    private Commodity commodity;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "commentId")
    private Set<Rate> rates = new HashSet<>();

//    private String userName;
   // HashMap<String, Integer> rating;
   // int commentId;
    public Comment(String inputUserEmail, int inputCommodityId, String inputText,
                        String inputDate)
    {
        userEmail = inputUserEmail;
        commodityId = inputCommodityId;
        text = inputText;
        date = inputDate;
    }

    public Comment(User user, Commodity commodity, String text, String date) {
        this.user = user;
        this.commodity = commodity;
        this.text = text;
        this.date = date;
    }

    public Comment() {
    }

//    public void setRatingEmpty()
//    {
//        rating = new HashMap<String, Integer>();
//    }
//    public void setUserName(String inputUserName){userName = inputUserName;}

    public String getUserEmail() {
        return userEmail;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

//    public String getUserName() {
//        return userName;
//    }

//    public int getLikes() {
//        int likes = 0;
//        for(int rate: rating.values())
//        {
//            if(rate == 1)
//                likes += 1;
//        }
//        return likes;
//    }

//    public int getDisLikes() {
//        int disLikes = 0;
//        for(int rate: rating.values())
//        {
//            if(rate == -1)
//                disLikes += 1;
//        }
//        return disLikes;
//    }

    public int getId() {
        return commentId;
    }

    public void setCommentId(int commentIdNow) {
        commentId = commentIdNow;
    }

//    public void addRate(User user, int rate) {
//        if(rating.containsKey(user.getName()))
//            rating.replace(user.getName(), rate);
//        else
//            rating.put(user.getName(), rate);
//    }
}

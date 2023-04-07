package Baloot;

import java.util.HashMap;

public class Comment {
    private String userEmail;
    private int commodityId;
    private String text;
    private String date;
    private String userName;
    HashMap<String, Integer> rating;
    int commentId;
    public Comment(String inputUserEmail, int inputCommodityId, String inputText,
                        String inputDate)
    {
        userEmail = inputUserEmail;
        commodityId = inputCommodityId;
        text = inputText;
        date = inputDate;
    }

    public void setRatingEmpty()
    {
        rating = new HashMap<String, Integer>();
    }
    public void setUserName(String inputUserName){userName = inputUserName;}

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

    public String getUserName() {
        return userName;
    }

    public int getLikes() {
        int likes = 0;
        for(int rate: rating.values())
        {
            if(rate == 1)
                likes += 1;
        }
        return likes;
    }

    public int getDisLikes() {
        int disLikes = 0;
        for(int rate: rating.values())
        {
            if(rate == -1)
                disLikes += 1;
        }
        return disLikes;
    }

    public int getId() {
        return commentId;
    }

    public void setCommentId(int commentIdNow) {
        commentId = commentIdNow;
    }

    public void addRate(User user, int rate) {
        if(rating.containsKey(user.getName()))
            rating.replace(user.getName(), rate);
        else
            rating.put(user.getName(), rate);
    }
}

package Baloot.DTOObjects;

import Baloot.Comment;
import Baloot.Vote;

import java.util.Set;

public class CommentDTO {
    private int id;
    private String userName;
    private String userEmail;
    private int commodityId;
    private String text;
    private String date;

    private long likesCount;
    private long dislikesCount;
    private int usersVote;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.userName = comment.getUser().getName();
        this.userEmail = comment.getUser().getEmail();
        this.commodityId = comment.getCommodity().getId();
        this.text = comment.getText();
        this.date = comment.getDate();

        setVotes(comment.getVotes());

    }

    public void setUsersVote(int vote)
    {
        usersVote = vote;
    }

    public void setVotes(Set<Vote> votes){


        for(Vote v : votes)
        {
            if(v.getVote()==1)
                likesCount++;
            else
                dislikesCount++;
        }
    }

    public int getId() {
        return id;
    }

    public int getCommentId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUsersVote() {
        return usersVote;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getLikes() {
        return likesCount;
    }

    public void setLikes(long likes) {
        this.likesCount = likes;
    }

    public long getDislikes() {
        return dislikesCount;
    }

    public void setDislikes(long dislikes) {
        this.dislikesCount = dislikes;
    }
}

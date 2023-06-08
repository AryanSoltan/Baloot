import axios from "axios";
import { useState } from "react";
import './Comments.css'

export default function CommentForm(props) {

    const { commodityId, addComment } = props;

    const isLoggedIn = localStorage.getItem('userLoggedIn');
    const userId = localStorage.getItem('userId');

    const [commentText, setCommentText] = useState('');
    const handleSubmit = async e => {
        e.preventDefault();
        try {
            console.log('in hnadle submit');
            console.log(commentText);
            console.log(userId);
            const data = { userId: userId, comment: commentText };
            const response = await axios.post('/commodities/'+commodityId+'/comment', data,  {headers: {Authorization: localStorage.getItem('token')}});
            const newComment = response.data.content;
            addComment(newComment);
            setCommentText('');
        } catch (e) {
            console.log(e);
        }
    }

    return (
<div className="comment-row comment-input">
        <p>Submit your opinion</p>
<div className="comment-input-part">


        <div className="row">
            <input   value={commentText}
                     required
                     onChange={e => { setCommentText(e.target.value) }}
                     className="form-control comment-input-box"
                   />

        </div>
        <div className="row">
            <button type="submit" className="btn button-type1" onClick={handleSubmit}>Post</button>
        </div>
</div>



</div>


    )
}
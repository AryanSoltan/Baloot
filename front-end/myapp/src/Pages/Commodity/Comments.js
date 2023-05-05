import axios from 'axios';
import './Comments.css';

export default function Comments(props) {

    const { comment, updateComment } = props;
    console.log('comment is ');
    console.log(comment);

    const handleVote = async vote => {
        try {
            console.log('in handle vote');
            const data = { vote };
            const response = await axios.post('/comments/' + comment.id + '/vote/', data);
            let newComment = response.data.content;
            updateComment(newComment);
        } catch (e) {
            console.log(e);
        }
    }

    return (

        <div className="comment-row">
            <div className="row comment-text">
                {comment.text}

            </div>
            <div className="row comment-info">
                {comment.userName}
                <span className="big-dot">.</span>
                {comment.date}
            </div>
            <div className="row comment-vote">
                Is this comment helpful?
                <span>1</span>
                <div class="like-icon" onClick={() => { handleVote(1) }}></div>
                <span>1</span>
                <div class="dislike-icon" onClick={() => { handleVote(-1) }}></div>
            </div>



        </div>

    )
}
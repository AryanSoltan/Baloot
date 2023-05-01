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


            {/*<div className="col-1">*/}
            {/*    <p>This was awsome!!!!!</p>*/}
            {/*    <div className="row d-flex justify-content-between">*/}
            {/*        <div className="row d-flex subtitle-format m-0">*/}
            {/*            <div className="col-lg-6 col-md-6">*/}
            {/*                2023-03-20*/}
            {/*            </div>*/}
            {/*            <div className="col-lg-1 col-md-1 big-dot">*/}
            {/*                .*/}
            {/*            </div>*/}
            {/*            <div className="col-lg-4 col-md-4">*/}
            {/*                {comment.userName}*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*        <div className="row d-flex align-items-center m-2 eval-comment-format">*/}
            {/*            Is this comment helpful?*/}
            {/*            <span>1</span>*/}
            {/*            <span className="like-icon"></span>*/}
            {/*            <span>1</span>*/}
            {/*            <span className="dislike-icon"></span>*/}
            {/*        </div>*/}
            {/*    </div>*/}

        {/*</div>*/}
        </div>

    )
}
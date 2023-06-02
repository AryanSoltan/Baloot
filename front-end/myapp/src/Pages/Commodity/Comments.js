import axios from 'axios';
import './Comments.css';
import { Link, useNavigate, useParams } from "react-router-dom"
import {useEffect, useState} from "react";


export default function Comments(props) {

    const { comment, updateComment } = props;


    // const [comment, updateComment] = useState();



    const userId = localStorage.getItem('userId');

    // useEffect(() => {
    //     async function fetchData() {
    //         try {
    //             const response = await axios.get('commodities/' + id);
    //             const commodityR= response.data.content;
    //
    //             console.log(commodityR);
    //
    //             setCommodity(commodityR);
    //         } catch (e) {
    //             if(e.response.status === 404) {
    //                 navigate('/404');
    //             }
    //         }
    //     }
    //
    //     fetchData();
    //
    // }, []);

    const handleLike = async vote => {
        try {
            const data = {comment_id: comment.id};
            const response = await axios.post("/commodities/" + comment.commodityId + "/" + userId + "/like", data);
            updateComment(response.data.data);
        } catch (e) {
            console.log(e);
        }
    }

    const handledisLike = async vote => {
        try {

            const data = {comment_id: comment.id};
            const response = await axios.post("/commodities/" + comment.commodityId + "/" + userId + "/dislike", data);
            console.log(response);
            updateComment(response.data.data);


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
                <span>{comment.likes}</span>
                <div class="like-icon" onClick={ handleLike }></div>
                <span>{comment.dislikes}</span>
                <div class="dislike-icon" onClick={ handledisLike }></div>
            </div>



        </div>

    )
}
import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import IMAGE from '../../assets/images/download.png'
import './Commodity.css';
import './Comments.css'
import RateCommodity from '../RateCommodity';
import Comment from './Comments'
import CommentForm from "./CommentForm";


export default function CommentsPart() {

    const [commodity, setCommodity] = useState();
    const { id } = useParams();

    const [error, setError] = useState('');

    const isLoggedIn = localStorage.getItem('userLoggedIn');
    const userId = localStorage.getItem('userId');
    console.log('user id is ');
    console.log(userId);

    const navigate = useNavigate();


    useEffect(() => {
        async function fetchData() {
            try {
                console.log("gettt");
                const response = await axios.get('commodities/' + id);
                const commodityR= response.data.content;

                console.log(commodityR);
                setCommodity(commodityR);
            } catch (e) {
                if(e.response.status === 404) {
                    navigate('/404');
                }
            }
        }

        fetchData();

    }, []);

    const updateRate = newCommodity => {
        commodity.rating = newCommodity.rating;

        setCommodity({...commodity});

    }
    const addComment = newComment => {
        commodity.comments.push(newComment);
        setCommodity({ ...commodity});
    }



    const updateComment = editedComment => {
        const commentIndex =  commodity.comments.findIndex(x => x.id === editedComment.id);
        commodity.comments[commentIndex] = editedComment;
        setCommodity({ ... commodity });
    }


    return (
        <>{commodity ?

<div className="container-fluid p-0 comment-container">

                <div className="comment-section">
                    <div className="panel-heading">
                        Comments
                        <span>(2)</span>
                    </div>
                    <div className="row">
                        {commodity.comments.map(comment => (
                            <Comment key={comment.id} comment={comment} updateComment={updateComment} />
                        ))}

                    </div>
                    <div className="row">
                        <CommentForm commodityId={commodity.id} addComment={addComment}  />

                    </div>

            </div>

</div>




            :
            <div class="text-center mt-5">
                <div class="spinner-border text-danger" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>



        }

        </>

    )
}
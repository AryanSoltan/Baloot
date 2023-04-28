import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import IMAGE from '../assets/images/download.png'
import './Commodity.css';
import './Comments.css'
import RateCommodity from './RateCommodity';
import Comment from './Comments'


export default function CommodityInfo() {

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


    const updateComment = editedComment => {
        const commentIndex =  commodity.comments.findIndex(x => x.id === editedComment.id);
        commodity.comments[commentIndex] = editedComment;
        setCommodity({ ... commodity });
    }


    return (
        <>{commodity ?
                <div className="container-fluid p-0">
                    <div className="row commodity-content-container">
                        <div className="col-1 ml-auto">
                            <img className="commodity-image" src={IMAGE} />
                        </div>


                        <div className="col-1 mx-auto">
                            <div className="row">
                                <div className="col-1">
                                    <div className="row info-part">
                                        <div className="col-1">
                            <div className="commodity-info-part">
                                                <h1>{commodity.name}</h1>
                                                <h2>{commodity.inStock} in In stock</h2>
                                                <h3>by <a href="#">{commodity.providerName}</a></h3>
                                                <h4>Categori(s)
                                                        <ul><li>Technology</li>
                                                                    <li>IT</li>
                                                        </ul>
                                                </h4>

                            </div>
                                </div>



                        <div className="col-1 mr-auto">
                            <div className="rate-star-row">
                                <p className="star-icon"></p>

                                <p>{commodity.rating}<span>(12)</span></p>
                        </div>
                            </div>
                                </div>
                                </div>
                    <div className="col-1 card item-buy-card">


                            <div className="card-body d-flex">
                                <div className="container-fluid row">
                                    <div className="col-lg-6 col-md-6">
                                        <span className="price price-large">300$</span>
                                    </div>

                                    <div className="col-lg-6 col-md-6 p-0">
                                        <a href="#" className="btn commodity-card-button">Add to card</a>
                                    </div>


                                </div>
                            </div>

                    </div>

                                <div className="col-1">

                                    <RateCommodity updateRate={updateRate} userId={userId} commodityId={commodity.id} userRate={commodity.rate}/>


                                </div>

                        </div>
                        </div>
                    </div>

{/*                    <div className="comment-section">*/}
{/*<div classNama="row">*/}
{/*                            <span>Comments</span>*/}
{/*                            {commodity.comments.map(comment => (*/}
{/*                                <Comment key={comment.id} comment={comment} updateComment={updateComment} />*/}
{/*                            ))}*/}

{/*</div>*/}
{/*                    </div>*/}
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
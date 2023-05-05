import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import IMAGE from '../../assets/images/download.png'
import './Commodity.css';
import './Comments.css'
import RateCommodity from '../RateCommodity';
import Comment from './Comments'
import IncrementDecrement from "./incdecButton";
import {Icon} from "@iconify/react";


export default function CommodityInfo() {

    const [commodity, setCommodity] = useState();
    const { id } = useParams();

    const [error, setError] = useState('');

    const isLoggedIn = localStorage.getItem('userLoggedIn');
    const userId = localStorage.getItem('userId');

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

    return (
        <>{commodity ?
                <div className="container-fluid p-0">
                    <div className="row commodity-content-container">
                        <div className="col-1 ml-auto">
                            <img className="commodity-image" src={commodity.image} />
                        </div>


                        <div className="col-1 mx-auto">
                            <div className="row">
                                <div className="col-1">
                                    <div className="row info-part">
                                        <div className="col-1">
                            <div className="commodity-info-part">

                                                <h1>{commodity.name}</h1>
                                                <h2>{commodity.inStock} in In stock</h2>
                                                <h3>by <a className="link" href={'http://localhost:3000/providers/'+commodity.id}>{commodity.providerName}</a></h3>
                                                <h4>Categori(s)
                                                        <ul>{commodity.categories && commodity.categories.map(cat =>
                                                            <li>{'\u2022'} {cat}</li> )}</ul>
                                                </h4>

                            </div>
                                </div>



                        <div className="col-1 mr-auto">
                            <div className="rate-star-row">

                                <Icon
                                    icon="codicon:star-full"
                                    className="fixed-star-icon"

                                />
                                <p>{commodity.rating}<span>(12)</span></p>
                        </div>
                            </div>
                                </div>
                                </div>
                    <div className="col-1 item-buy-card">

                            <div className="card-body">
                                <span className="price price-large">{commodity.price}$</span>
                                  <IncrementDecrement commodityId={commodity.id} currentCount={0} max={commodity.inStock}/>
                            </div>

                    </div>

                    <div className="col-1 rateTitle">
                         rate now
                         <RateCommodity updateRate={updateRate} userId={userId} commodityId={commodity.id} userRate={commodity.rate}/>


                    </div>

                        </div>
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
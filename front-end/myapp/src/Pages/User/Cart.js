
import * as React from "react";
import {toast} from "react-toastify";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import cartPNG from "../../assets/images/cart.png"
import historyPNG from "../../assets/images/history.png"

import Row from 'react-bootstrap/Row';

import IncrementDecrement from "../Commodity/incdecButton"

import 'bootstrap/dist/css/bootstrap.min.css';
import '../Commodity/incdecButton.css';

import "../../Style/User.css"

import axios from 'axios'

export default function UserInfo()
{

    const [buyListCommodities, setBuyListCommodities] = useState();
    const [purchesedListCommodities, setPurchasedListCommodities] = useState();
    const changePage = useState();


    useEffect(() => {

        const userId = localStorage.getItem('userId');

        async function fetchData() {

            try {
                const response = await axios.post("/users/" + "1/" + "add",
                    {userId: userId,
                    }
                );
            } catch (e) {
                console.log(e);
            }

            try {
                const response = await axios.get("/users/" + userId +"/buyList");
                const commodititesList = response.data.content;
                console.log(commodititesList);

                setBuyListCommodities(commodititesList.allCommodities);
                console.log(commodititesList.allCommodities)


            } catch (e) {
                console.log(e);
            }

            try {
                const response = await axios.get("/users/" + userId +"/purchasedList");
                const commodititesList = response.data.content;
                console.log(commodititesList);

                setPurchasedListCommodities(commodititesList.allCommodities);
                console.log(commodititesList.allCommodities);
            } catch (e) {
                console.log(e);
            }
        }
        fetchData();
    }, []);

    const handleIncrease = (e) => {
        console.log("Increase");
        const userId = localStorage.getItem('userId');

        try {
            const response = axios.post("/users/" + e + "/add",
                {userId: userId,
                }
            );
        } catch (e) {
            console.log(e);
        }
    };

    const handleDecrease = (e) => {
        console.log("Decrease");
        const userId = localStorage.getItem('userId');

        try {
            const response = axios.post("/users/" + e + "/remove",
                {userId: userId,
                }
            );
        } catch (e) {
            console.log(e);
        }
    };

    const handlePayment = (e) => {
        const userId = localStorage.getItem('userId');
        try {
            const response = axios.post("/users/" + userId + "/buyList/submit");
        } catch (e) {
            console.log(e);
        }
        window.location.reload();
    };



    return(
        <section className="section-cart-info">
            <div className="cart-item-info">
                <img src={cartPNG}/>
                <p className="cart-title-txt">Cart</p>
            </div>

            <table  class="table">
                <tr className="cart-col-name">
                    <th>Image</th>
                    <th>Name</th>
                    <th>Categories</th>
                    <th>Price</th>
                    <th>Provider ID</th>
                    <th>Rating</th>
                    <th>In Stock</th>
                    <th>In Cart</th>
                </tr>


                {buyListCommodities &&
                    buyListCommodities.map((item) => (
                        <tr className = "cart-buy-item">
                            <td><img src = {item.commodity.image}></img></td>
                            <td><p>{item.commodity.name}</p></td>
                            <td><p>{item.commodity.categories}</p></td>
                            <td><p>${item.commodity.price}</p></td>
                            <td><p>{item.commodity.providerId}</p></td>
                            <td><h5 className="col-yellow">{item.commodity.rating}</h5></td>
                            <td><h5 className="col-red">{item.commodity.inStock}</h5></td>
                            <td>
                                <IncrementDecrement className = "button-user" commodityId={item.commodity.id} currentCount={item.numInStock} max={item.commodity.inStock}/>
                            </td>
                        </tr>

                    ))}


            </table>
            <button className="payment-button" onClick = {handlePayment}>
                Pay now!
            </button>


                <div className="cart-item-info">
                    <img src={historyPNG}/>
                    <p className="cart-title-txt">History</p>
                </div>

                <table className="table">
                    <tr className="cart-col-name">
                        <th>Image</th>
                        <th>Name</th>
                        <th>Categories</th>
                        <th>Price</th>
                        <th>Provider ID</th>
                        <th>Rating</th>
                        <th>In Stock</th>
                        <th>Quantity</th>
                    </tr>

            {purchesedListCommodities &&
                purchesedListCommodities.map((item) => (
                    <tr className = "cart-buy-item">
                        <td><img src = {item.commodity.image}></img></td>
                        <td><p>{item.commodity.name}</p></td>
                        <td><p>{item.commodity.categories}</p></td>
                        <td><p>${item.commodity.price}</p></td>
                        <td><p>{item.commodity.providerId}</p></td>
                        <td><h5 className="col-yellow">{item.commodity.rating}</h5></td>
                        <td><h5 className="col-red">{item.commodity.inStock}</h5></td>
                        <td>
                            <IncrementDecrement className = "button-user" commodityId={item.commodity.id} currentCount={item.numInStock} max={item.commodity.inStock}/>
                        </td>
                    </tr>

                ))}



                </table>
        </section>
    )
}
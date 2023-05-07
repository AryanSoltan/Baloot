
import * as React from "react";
import {toast} from "react-toastify";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import cartPNG from "../../assets/images/cart.png"
import historyPNG from "../../assets/images/history.png"

import Row from 'react-bootstrap/Row';

import IncrementDecrement from "../Commodity/incdecButton"

// import 'bootstrap/dist/css/bootstrap.min.css';
import '../Commodity/incdecButton.css';

import "../../Style/User.css"
import "./Cart.css"

import axios from 'axios'

import Popup from 'reactjs-popup';
export default function UserInfo()
{

    const [buyListCommodities, setBuyListCommodities] = useState();
    const [purchesedListCommodities, setPurchasedListCommodities] = useState();
    const changePage = useState();


    useEffect(() => {

        const userId = localStorage.getItem('userId');

        async function fetchData() {


            // try {
            //     const response = await axios.post("/users/" + "1/" + "add",
            //         {userId: userId,
            //         }
            //     );
            // } catch (e) {
            //     console.log(e);
            // }

            try {
                const response = await axios.get("/users/" + userId +"/buyList");
                const commodititesList = response.data.content;
                console.log(commodititesList);

                setBuyListCommodities(commodititesList.allCommodities);
                console.log(commodititesList.allCommodities)
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
            toast.error(e.response);
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


                {Array.isArray(buyListCommodities) && buyListCommodities.length?
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

                    )):<p></p>}

            </table>
            {
                !Array.isArray(buyListCommodities)  || !buyListCommodities.length &&
                <p className="empty-cart">your cart is empty</p>
            }


            <Popup trigger=
                       {    <button className="payment-button" onClick = {handlePayment}>
                           Pay now!
                       </button>}
                   modal nested>
                {
                    close => (
                        <div className="popup-window">
                           <h1>Your cart</h1>

                                <ul>{buyListCommodities && buyListCommodities.map(item =>
                                    <li>
                                        <div className="items-row">
                                        {'\u2022'} {item.commodity.name} * {item.numInStock}
                                    </div>
                                        <div className="price-row">
                                            ${item.commodity.price}
                                        </div>
                                    </li> )}</ul>
                            <div className="discount-input-part">
                                <div className="row">
                                    <input
                                             // onChange={e => { setCommentText(e.target.value) }}
                                             className="form-control discount-input-box"
                                    />
                                </div>
                                <div className="row">
                                    <button type="submit" className="btn" >Submit</button>
                                </div>
                            </div>
                            <ul>
                            <li>
                                <div className="items-row">
                                   total
                                </div>
                                <div className="price-row">
                                    $price
                                </div>
                            </li>
                            </ul>

                            <div className="buttons">

                                <button className="btn exit" onClick=
                                            {() => close()}>
                                   exit
                                </button>

                                    <button type="submit" className="btn buy" onClick={handlePayment} >Buy!</button>

                            </div>
                            
                        </div>
                    )
                }
            </Popup>


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
                            <p>{item.numInStock}</p>
                        </td>
                    </tr>

                ))}



                </table>
        </section>
    )
}

import * as React from "react";
import { toast, ToastContainer } from "react-toastify";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import cartPNG from "../../assets/images/cart.png"
import historyPNG from "../../assets/images/history.png"

import Row from 'react-bootstrap/Row';

import IncrementDecrement from "../Commodity/incdecButton"

// import 'bootstrap/dist/css/bootstrap.min.css';
import '../Commodity/incdecButton.css';

// import "../../Style/User.css"
import "./Cart.css"

import axios from 'axios'

import Popup from 'reactjs-popup';

toast.configure();
const notify = (message) => toast(message);
export default function UserInfo()
{

    const [buyListCommodities, setBuyListCommodities] = useState();
    const [purchesedListCommodities, setPurchasedListCommodities] = useState();
    const changePage = useState();
    const [discountText, setDiscountText] = useState('');
    const [discountPercent, setDiscountPercent] = useState(0);
    const [totalPrice, setTotalPrice] = useState(0);

    const [inactive, setInActive] = useState(false);

    const userId = localStorage.getItem('userId');


    useEffect(() => {



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
                setTotalPrice(commodititesList.buylistPrice);

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



    const handlePayment = (e) => {

        const data = { discountCode: discountText };
        // axios.post('/users/'+userId+'/buyList/applyDiscount/', data);
        axios.post("/users/" + userId + "/buyList/submit/",data).then((resp) => {

            if(resp.data.statusCode === 200) {
                window.location.reload();
             }
            else
            {
              toast.error(resp.data.data);
            }
             }).catch(error => {
             console.log(error);
           });

    };

    const closePopup = (e) => {
        setDiscountText("");
        setDiscountPercent(0);
        setInActive(false);

    };



    const handleDiscountCodeSubmit = async e => {
        e.preventDefault();
        try {


            const data = { discountCode: discountText };
            const response = await axios.post('/users/'+userId+'/buyList/validateDiscount/', data);
            const discountCode = response.data.content;

            console.log('in hnadle discount');

            setDiscountPercent(discountCode.percentage);
            setInActive(true);

        } catch (e) {
            toast.error("discount code is not valid");
            console.log(e);
        }
    }



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
                       {    <button className="payment-button">
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
                                             onChange={e => { setDiscountText(e.target.value); setInActive(false); }}
                                             className="form-control discount-input-box"
                                    />
                                </div>
                                <div className="row">
                                    <button type="submit" className={inactive ? "inactive-btn":"active-btn"} onClick={handleDiscountCodeSubmit}>
                                        {inactive ? "Submitted" : "Submit"}
                                    </button>
                                </div>
                            </div>
                            <ul>
                            <li>
                                <div className="items-row">
                                    total
                                </div>
                                <div className={"price-row"}>
                                    {discountPercent>0 ? <span className="inactive">${totalPrice}</span> : <span className="active">${totalPrice}</span>}
                                </div>
                            </li>
                                {discountPercent>0 &&
                                    <li>

                                <div className="items-row">
                                   with discount
                                </div>
                                <div className="price-row">
                                    <span className="active">${totalPrice-(totalPrice*discountPercent)/100}</span>

                                    </div>
                                    </li>
                                }

                            </ul>

                            <div className="buttons">

                                <button className="btn exit"  onClick={() => {close();closePopup();}} >
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

import * as React from "react";
import {toast} from "react-toastify";
import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom"

import cartPNG from "../../assets/images/cart.png"
import Row from 'react-bootstrap/Row';


import 'bootstrap/dist/css/bootstrap.min.css';



import "../../Style/User.css"

import axios from 'axios'

export default function UserInfo()
{

    const [buyListCommodities, setBuyListCommodities] = useState();



    useEffect(() => {

        const userId = localStorage.getItem('userId');

        async function fetchData() {

            try {
                const response = await axios.post("/commodities/" + "1/" + userId + "/add");
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

        }
        fetchData();
    }, []);

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
                            <td><img src = {item.image}></img></td>
                            <td><p>{item.name}</p></td>
                            <td><p>{item.categories}</p></td>
                            <td><p>${item.price}</p></td>
                            <td><p>{item.providerId}</p></td>
                            <td><h5 className="col-yellow">{item.rating}</h5></td>
                            <td><h5 className="col-red">{item.inStock}</h5></td>
                            <td>
                                <form className = "form-dec-inc">
                                        <button className="btn-inc-dec">+</button>
                                        <div className="number-buy">100</div>
                                        <button className="btn-inc-dec">-</button>
                                </form>
                            </td>
                        </tr>
                    ))}

            </table>



            {/*<div className="cart-col-name">*/}
            {/*    <div className="row">*/}
            {/*        <p className="box-cart-name">Image</p>*/}
            {/*        <p className="box-cart-name">Name</p>*/}
            {/*        <p className="box-cart-name">Categories</p>*/}
            {/*        <p className="box-cart-name">Price</p>*/}
            {/*        <p className="box-cart-name">Provider ID</p>*/}
            {/*        <p className="box-cart-name">Rating</p>*/}
            {/*        <p className="box-cart-name">In Stock</p>*/}
            {/*        <p className="box-cart-name">In Cart</p>*/}
            {/*    </div>*/}
            {/*</div>*/}

            {/*<div className="cart-buy-item">*/}
            {/*    <div className="row">*/}
            {/*        <div className="box-cart-item"><img src="../assets/images/item-in-cart.png"/></div>*/}
            {/*        <div className="box-cart-item">*/}
            {/*            <p>Galexy S21</p>*/}
            {/*        </div>*/}
            {/*        <div className="box-cart-item">*/}
            {/*            <p> Technology, Phone</p>*/}
            {/*        </div>*/}
            {/*        <div className="box-cart-item">*/}
            {/*            <p>$21000000</p>*/}
            {/*        </div>*/}
            {/*        <div className="box-cart-item">*/}
            {/*            <p>1234</p>*/}
            {/*        </div>*/}
            {/*        <div className="box-cart-item">*/}
            {/*            <h5 className="col-yellow">8.3</h5>*/}
            {/*        </div>*/}
            {/*        <div className="box-cart-item">*/}
            {/*            <h5 className="col-red">17</h5>*/}
            {/*        </div>*/}
            {/*        <div className="box-cart-item inc-dec">*/}
            {/*            <div className="row">*/}
            {/*                <button className="astext">+</button>*/}
            {/*                <h6>1</h6>*/}
            {/*                <button className="astext">-</button>*/}
            {/*            </div>*/}
            {/*        </div>*/}
            {/*    </div>*/}
            {/*</div>*/}


            <button className="payment-button">
                Pay now!
            </button>

        </section>
    )
}
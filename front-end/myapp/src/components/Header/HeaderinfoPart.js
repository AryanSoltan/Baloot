import React, {useState, useEffect, useContext} from "react";
import { useNavigate } from "react-router-dom";
import './Navbar.css';
import logo from "../../assets/images/ballot.png";
import './NavbarLogo.css'
import axios from "axios";

import {ExampleContext} from './../../Pages/Commodity/incdecButton.js';
import {Context} from "../../App";

function HeaderInfoPart() {

    const [itemCounts, setitemCounts] = useState(0);

    const [itemC, setitemC] = useState(0);

    var isLoggedIn = localStorage.getItem('userLoggedIn');
    var userId = localStorage.getItem('userId');

    const {value} = useContext(Context)
    console.log("vale us");
    console.log(value);

    useEffect(() => {
        async function fetchData() {
            try {
                console.log('in  item');
                console.log(value);

                const response = await axios.get("/users/"+userId+"/buyList", {headers: {Authorization: localStorage.getItem('token')}});
                const userBuyList = response.data.content;
                console.log("bulist is ");
                console.log(userBuyList);
                var cardCount = 0;
                for (let i = 0; i < userBuyList.allCommodities.length; i++) {
                    console.log(userBuyList.allCommodities[i]);
                    cardCount += userBuyList.allCommodities[i].countInBuylist;
                }

                setitemCounts(cardCount);

            } catch (e) {
                console.log(e);
            }
        }
        fetchData();
    }, [value]);

    //
    // const handleSignup = async () => {
    //
    // };
    //
    // const handleLogin = async () => {
    //
    // };


    if (isLoggedIn === "false") {

        return (
            <div></div>

        );
    } else if (isLoggedIn === "true") {

        return (
            <>
                {/*<div className="mr-auto">*/}
                {/*    <ul className="navbar-nav logged-in-state align-items-center">*/}
                {/*        <li className="nav-item">*/}
                {/*            <a href="#">*/}
                {/*                <span className="header-username-container">#{userId}</span>*/}
                {/*            </a>*/}
                {/*        </li>*/}
                {/*        <li className="nav-item">*/}
                {/*            <a href="#">*/}
                {/*                <button type="button" className="btn btn-default count-of-bought-items">*/}
                {/*                    Card*/}
                {/*                    <span className="badge">3</span>*/}


                {/*                </button>*/}
                {/*            </a>*/}
                {/*        </li>*/}

                {/*    </ul>*/}
                {/*</div>*/}
                <div className="header-info-part">
                    {/*<div className="row">*/}

                        <div className="col header-username-container">
                           <span><a className="link" href={'http://localhost:3000/user'}>
                            {userId} </a></span>

                        </div>

                        <div className="col header-btn-container">
                            <a className="link" href={'http://localhost:3000/user'}>
                            <button type="button" className={itemCounts>0 ? "active-card-btn":"inactive-card-btn"}>
                            Card
                                    <span className="badge">{itemCounts}</span>
                            </button>
                            </a>

                        </div>
                    {/*</div>*/}
                </div>
            </>
        );
    }
}

export default HeaderInfoPart;
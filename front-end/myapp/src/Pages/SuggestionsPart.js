import axios from "axios";
import React, { useEffect, useState } from "react";
import {Link, useLocation, useParams} from "react-router-dom";
import CommodityCard from "./Commodity/CommodityCard";
import IMAGE from '../assets/images/item-in-cart.png'
import './Commodities/Commodities.css'
import './Commodity/Commodity.css'

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Card from 'react-bootstrap/Card';



export default function SuggestionsPart() {
    const [commodities, setcommodities] = useState();
    const [fetchedCommodities, setFetchedCommodities] = useState();

    const { id } = useParams();
    const userId = localStorage.getItem('userId');


    useEffect(() => {
        async function fetchData() {
            try {
                const response = await axios.get("commodities/"+id+'/'+userId+'/suggestions',  {headers: {Authorization: localStorage.getItem('token')}});
                const commodititesList = response.data.content;
                console.log('commodities list');
                console.log(commodititesList);


                setcommodities(commodititesList);

                setFetchedCommodities(commodititesList);

            } catch (e) {
                console.log(e);
            }
        }
        fetchData();
    }, []);
    return (
        <>
<div className="suggestions-section">
    {commodities && <div className="suggestionTitle">You also might like ...</div>}
            <div className="commodities-list">


                    {commodities &&
                    commodities.map((item) => (
                        <div className="" key={item.id}>
                            {/*<Link to={"/commoditites/" + item.id}>*/}
                            <CommodityCard
                                commodity={item}

                            />



                        </div>

                    ))}
            </div>
</div>

        </>
    );
}

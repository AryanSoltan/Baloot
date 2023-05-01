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


    useEffect(() => {
        async function fetchData() {
            try {
                const response = await axios.get("commodities/"+id+'/suggestions');
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
    <h1>You also might like...</h1>

            <div className="commodities-list">

                {commodities &&
                    commodities.map((item) => (
                        <div className="" key={item.id}>
                            {/*<Link to={"/commoditites/" + item.id}>*/}
                            <CommodityCard
                                image={IMAGE}
                                name={item.name}
                                price={item.price}
                                count = {item.inStock}/>

                            {/*</Link>*/}

                        </div>
                    ))}
            </div>
</div>

        </>
    );
}

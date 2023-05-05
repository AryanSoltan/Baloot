import '../Commodities/CommoditiesCard.css';
import { Link, useNavigate, useParams } from "react-router-dom"
// import RnIncrementDecrementBtn  from 'react-native-input-spinner';
import IncrementDecrement from "./incdecButton";
import Card from 'react-bootstrap/Card';
export default function CommodityCard(props) {
    const {image, name, price, count, id} = props;



    return (

<>
    <div class="commodity-card">
        <div class="card-title">

                <a className="link" href={'http://localhost:3000/commodities/'+id}><h1>{name}</h1></a>

            <h2>{count} in stock</h2>
        </div>
        <img className="card-img" src={image} alt={name}/>
            <div className="card-bottom p-3">
                <div className="container-fluid row">
                    <div className="col-lg-6 col-md-6">
                        <span className="price price-small">{price}$</span>

                        {/*<a href="#" className="btn commodity-card-button">Add to card</a>*/}

                        <IncrementDecrement commodityId={id} currentCount={0} max={count}/>
                    </div>


                </div>
            </div>

    </div>
</>






    );
}

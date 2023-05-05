import '../Commodities/CommoditiesCard.css';
import { Link, useNavigate, useParams } from "react-router-dom"
// import RnIncrementDecrementBtn  from 'react-native-input-spinner';
import IncrementDecrement from "./incdecButton";
import Card from 'react-bootstrap/Card';
export default function CommodityCard(props) {
    const {commodity} = props;



    return (

<>
    <div class="commodity-card">
        <div class="card-title">

                <a className="link" href={'http://localhost:3000/commodities/'+commodity.id}><h1>{commodity.name}</h1></a>

            <h2>{commodity.inStock} in stock</h2>
        </div>
        <img className="card-img" src={commodity.image}  alt={commodity.name}/>
            <div className="card-bottom p-3">
                <div className="container-fluid row">
                    <div className="col-lg-6 col-md-6">
                        <span className="price price-small">{commodity.price}$</span>

                        {/*<a href="#" className="btn commodity-card-button">Add to card</a>*/}

                        <IncrementDecrement commodityId={commodity.id} currentCount={0} max={commodity.inStock}/>
                    </div>


                </div>
            </div>

    </div>
</>






    );
}

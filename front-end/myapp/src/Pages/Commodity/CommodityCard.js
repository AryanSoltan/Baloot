import '../Commodities/CommoditiesCard.css';
import Card from 'react-bootstrap/Card';
export default function CommodityCard(props) {
    const {image, name, price, count} = props;


    return (

<>
    <div class="commodity-card">
        <div class="card-title">
            <h1>{name}</h1>
            <h2>{count} in stock</h2>
        </div>
        <img className="card-img" src={image} alt={name}/>
            <div className="card-bottom p-3">
                <div className="container-fluid row">
                    <div className="col-lg-6 col-md-6">
                        <span className="price price-small">{price}$</span>

                        <a href="#" className="btn commodity-card-button">Add to card</a>
                    </div>


                </div>
            </div>

    </div>
</>






    );
}

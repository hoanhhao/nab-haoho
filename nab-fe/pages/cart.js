import Layout from '../components/Layout';
import {useState} from "react";
import axios from "axios";

const Cart = () => {
    const [state, setState] = useState({
        items: JSON.parse(sessionStorage.getItem('cart')).filter((item) => item.order),
        buttonText: 'Submit',
        errorMessage: ''
    });

    const {items, buttonText } = state;

    const handleSubmit = () => e => {
        let order = [];
        for (var i=0; i < state.items.length; i++) {
            let item = state.items[i];
            order.push({
                'productId': item.id,
                'price': item.price,
                'quantity': item.order,
                'total': item.total
            })
        }
        axios
            .post(`http://localhost:8765/order-svc/api/orders/proceed`, order)
            .then(response =>
                setState({ ...state, items: response.data })
            )
            .catch(error => console.log(error));
        window.history.back();
    };

    const CartList = () =>
        <div>
            <button className="btn btn-outline-warning" onClick={handleSubmit()}>{buttonText}</button>
            <table border='1' className="table">
                <thead>
                    <tr>
                        <td>Product Id</td>
                        <td>Name</td>
                        <td>Price</td>
                        <td>Quantity</td>
                        <td>Total</td>
                    </tr>
                </thead>
                <tbody>
                {state.items.map((item, index) => {
                    return (
                        <tr key={index}>
                            <td>
                                {item.id}
                            </td>
                            <td>{item.name}</td>
                            <td>{item.price}</td>
                            <td>{item.order}</td>
                            <td>{item.total}</td>
                        </tr>
                    )
                })
                }
                </tbody>
            </table>
        </div>;

    return (
        <Layout>
            {CartList()}
            <span style={{color:"red"}}>{state.errorMessage}</span><br/>
            {/*{JSON.stringify(state)}<br/>*/}
        </Layout>
    );
}

export default Cart;

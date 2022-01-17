import Layout from '../components/Layout';
import {useState} from "react";
import axios from "axios";

const Product = () => {
    const [state, setState] = useState({
        productId: '',
        name: '',
        category: '',
        brand: '',
        color: '',
        items: [],
        buttonText: 'Search',
        errorMessage: ''
    });

    const { productId, name, category, brand, color, items, buttonText } = state;

    const handleChange = name => e => {
        setState({ ...state, [name]: e.target.value, error: '', success: '' });
    };

    const handleQuantityUpdate = (quantity, item) => e => {
        item.order = (item.order || 0) + quantity;
        if (item.order < 0) {
            item.order = 0;
        }
        item.total = item.order * item.price;

        if (item.order > item.quantity) {
            setState({...state, errorMessage: `Item ${item.name} is out of stock`});
            item.order = 0;
        } else {
            setState({...state, errorMessage: ''});
            sessionStorage.setItem('cart', JSON.stringify(state.items));
            console.log(sessionStorage.getItem('cart'));
        }
    };

    const handleSubmit = () => e => {
        e.preventDefault();
        axios
            .get(`http://localhost:8765/product-svc/api/products/list?name=${state.name}&category=${state.category}&brand=${state.brand}&color=${state.color}`)
            .then(response =>
                setState({ ...state, items: response.data })
            )
            .catch(error => console.log(error));
    };

    const ProductList = () =>
        <div>
            <table border='0' className="table">
                <thead>
                <tr>
                    <td>
                    </td>
                    <td><input
                        value={name}
                        onChange={handleChange('name')}
                        type="text"
                        style={{width: '150px'}}
                        className="form-control"
                        placeholder="Name"
                    /></td>
                    <td><input
                        value={category}
                        onChange={handleChange('category')}
                        style={{width: '150px'}}
                        type="text"
                        className="form-control"
                        placeholder="Category"
                    /></td>
                    <td><input
                        value={brand}
                        onChange={handleChange('brand')}
                        style={{width: '150px'}}
                        type="text"
                        className="form-control"
                        placeholder="Brand"
                    /></td>
                    <td><input
                        value={color}
                        onChange={handleChange('color')}
                        style={{width: '150px'}}
                        type="text"
                        className="form-control"
                        placeholder="Color"
                    /></td>
                    <td>
                        <div className="form-group">
                            <button className="btn btn-outline-warning" onClick={handleSubmit()}>{buttonText}</button>
                        </div>
                    </td>
                </tr>
                    <tr>
                        <th></th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Brand</th>
                        <th>Color</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>

                    {state.items.map((value, index) => {
                        return (
                            <tr key={index}>
                                <td>
                                    <div className="form-group">
                                        <button className="btn btn-outline-warning" onClick={handleQuantityUpdate(-1, value)}>-</button>
                                        <span>{value.order || 0}</span>
                                        <button className="btn btn-outline-warning" onClick={handleQuantityUpdate(1, value)}>+</button>
                                    </div>
                                </td>
                                <td>{value.name}</td>
                                <td>{value.category}</td>
                                <td>{value.brand}</td>
                                <td>{value.color}</td>
                                <td>{value.price}</td>
                            </tr>
                        )
                    })
                    }
                </tbody>
            </table>
        </div>;

    return (
        <Layout>
            {ProductList()}
            <span style={{color:"red"}}>{state.errorMessage}</span><br/>
            {/*{JSON.stringify(state)}*/}
        </Layout>
    );
}

export default Product;

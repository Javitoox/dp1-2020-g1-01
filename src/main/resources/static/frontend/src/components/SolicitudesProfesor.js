import axios from 'axios';
import {Component} from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
export default class ProductService {

    getProductsSmall() {
		return axios.get('showcase/demo/data/products-small.json').then(res => res.data.data);
	}

	getProducts() {
		return axios.get('showcase/demo/data/products.json').then(res => res.data.data);
    }

    getProductsWithOrdersSmall() {
		return axios.get('showcase/demo/data/products-orders-small.json').then(res => res.data.data);
	}
}
export class SolicitudesProfesor extends Component {
    // baseUrl = "http://localhost:8081/solicitudes";

    // getAllSolicitudes(){
    //     return axios.get(this.baseUrl + "/all").then(res => res.data);
    //  }
        constructor(props) {
            super(props);
            this.state = {
                products: []
            };
    
            this.productService = new ProductService();
        }
        render() {
            return (
                <div>
                    <div className="card">
                        <DataTable value={this.state.products}>
                            <Column field="nick_usuario" header="Nombre"></Column>
                            <Column field="fecha_solicitud" header="Fecha de la solicitud"></Column>
                        </DataTable>
                    </div>
                </div>
            );
        }
    
}
       

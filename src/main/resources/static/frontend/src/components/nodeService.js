import axios from 'axios';


export default class nodeService {
    getTreeNodes() {
        return axios.get('http://localhost:8081/course/all').then(res => res.data);
    }
}
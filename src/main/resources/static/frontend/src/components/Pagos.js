import React, { useState, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { CustomerService } from '../service/CustomerService';
import { InputText } from 'primereact/inputtext';
import { MultiSelect } from 'primereact/multiselect';
import { Dropdown } from 'primereact/dropdown';

const DataTableStateDemo = () => {
    const [customers, setCustomers] = useState(null);
    const [globalFilter1, setGlobalFilter1] = useState(null);
    const [globalFilter2, setGlobalFilter2] = useState(null);
    const [globalFilter3, setGlobalFilter3] = useState(null);
    const [selectedCustomer1, setSelectedCustomer1] = useState(null);
    const [selectedCustomer2, setSelectedCustomer2] = useState(null);
    const [selectedCustomer3, setSelectedCustomer3] = useState(null);
    const [selectedRepresentative, setSelectedRepresentative] = useState(null);
    const [selectedStatus, setSelectedStatus] = useState(null);
    const representatives = [
        { name: "Amy Elsner", image: 'amyelsner.png' },
        { name: "Anna Fali", image: 'annafali.png' },
        { name: "Asiya Javayant", image: 'asiyajavayant.png' },
        { name: "Bernardo Dominic", image: 'bernardodominic.png' },
        { name: "Elwin Sharvill", image: 'elwinsharvill.png' },
        { name: "Ioni Bowcher", image: 'ionibowcher.png' },
        { name: "Ivan Magalhaes", image: 'ivanmagalhaes.png' },
        { name: "Onyama Limba", image: 'onyamalimba.png' },
        { name: "Stephen Shaw", image: 'stephenshaw.png' },
        { name: "XuXue Feng", image: 'xuxuefeng.png' }
    ];

    const dataTableFuncMap = {
        'globalFilter1': setGlobalFilter1,
        'globalFilter2': setGlobalFilter2,
        'globalFilter3': setGlobalFilter3
    };

    const statuses = [
        'unqualified', 'qualified', 'new', 'negotiation', 'renewal', 'proposal'
    ];

    const customerService = new CustomerService();

    useEffect(() => {
        customerService.getCustomersMedium().then(data => setCustomers(data));
    }, []); // eslint-disable-line react-hooks/exhaustive-deps

    const onCustomSaveState = (state) => {
        window.sessionStorage.setItem('dt-state-demo-custom', JSON.stringify(state));
    }

    const onCustomRestoreState = () => {
        return JSON.parse(window.sessionStorage.getItem('dt-state-demo-custom'));
    }

    const countryBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <img alt={rowData.country.code} src="showcase/demo/images/flag_placeholder.png" onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} className={`flag flag-${rowData.country.code}`} width="30" />
                <span className="image-text">{rowData.country.name}</span>
            </React.Fragment>
        );
    }

    const representativeBodyTemplate = (rowData) => {
        return (
            <React.Fragment>
                <img alt={rowData.representative.name} src={`showcase/demo/images/avatar/${rowData.representative.image}`} onError={(e) => e.target.src='https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png'} width="32" style={{ verticalAlign: 'middle' }} />
                <span className="image-text">{rowData.representative.name}</span>
            </React.Fragment>
        );
    }

    const statusBodyTemplate = (rowData) => {
        return <span className={`customer-badge status-${rowData.status}`}>{rowData.status}</span>;
    }

    const renderHeader = (globalFilterKey) => {
        return (
            <span className="p-input-icon-left">
                <i className="pi pi-search" />
                <InputText type="search" onInput={(e) => dataTableFuncMap[`${globalFilterKey}`](e.target.value)} placeholder="Global Search" />
            </span>
        );
    }

    const header1 = renderHeader('globalFilter1');
    const header2 = renderHeader('globalFilter2');
    const header3 = renderHeader('globalFilter3');
    const representativeFilter = <MultiSelect value={selectedRepresentative} options={representatives} onChange={(e) => setSelectedRepresentative(e.value)} optionLabel="name" optionValue="name" placeholder="All" className="p-column-filter" />;
    const statusFilter = <Dropdown value={selectedStatus} options={statuses} onChange={(e) => setSelectedStatus(e.value)} placeholder="Select a Status" className="p-column-filter" showClear />;

    return (
        <div>
            <div className="card">
                <h5>Session Storage</h5>
                <DataTable value={customers} paginator rows={10} header={header1} globalFilter={globalFilter1}
                    selection={selectedCustomer1} onSelectionChange={e => setSelectedCustomer1(e.value)} selectionMode="single" dataKey="id"
                    stateStorage="session" stateKey="dt-state-demo-session" emptyMessage="No customers found.">
                    <Column field="name" header="Name" sortable filter filterPlaceholder="Search by name"></Column>
                    <Column header="Country" body={countryBodyTemplate} sortable sortField="country.name" filter filterField="country.name" filterMatchMode="contains" filterPlaceholder="Search by country"></Column>
                    <Column header="Representative" body={representativeBodyTemplate} sortable sortField="representative.name" filter filterField="representative.name" filterMatchMode="in" filterElement={representativeFilter}></Column>
                    <Column field="status" header="Status" body={statusBodyTemplate} sortable filter filterMatchMode="equals" filterElement={statusFilter}></Column>
                </DataTable>
            </div>

            <div className="card">
                <h5>Local Storage</h5>
                <DataTable value={customers} paginator rows={10} header={header2} globalFilter={globalFilter2}
                    selection={selectedCustomer2} onSelectionChange={e => setSelectedCustomer2(e.value)} selectionMode="single" dataKey="id"
                    stateStorage="local" stateKey="dt-state-demo-local" emptyMessage="No customers found.">
                    <Column field="name" header="Name" sortable filter filterPlaceholder="Search by name"></Column>
                    <Column header="Country" body={countryBodyTemplate} sortable sortField="country.name" filter filterField="country.name" filterMatchMode="contains" filterPlaceholder="Search by country"></Column>
                    <Column header="Representative" body={representativeBodyTemplate} sortable sortField="representative.name" filter filterField="representative.name" filterMatchMode="in" filterElement={representativeFilter}></Column>
                    <Column field="status" header="Status" body={statusBodyTemplate} sortable filter filterMatchMode="equals" filterElement={statusFilter}></Column>
                </DataTable>
            </div>

            <div className="card">
                <h5>Custom Storage</h5>
                <DataTable value={customers} paginator rows={10} header={header3} globalFilter={globalFilter3}
                    selection={selectedCustomer3} onSelectionChange={e => setSelectedCustomer3(e.value)} selectionMode="single" dataKey="id"
                    stateStorage="custom" customSaveState={onCustomSaveState} customRestoreState={onCustomRestoreState} emptyMessage="No customers found.">
                    <Column field="name" header="Name" sortable filter filterPlaceholder="Search by name"></Column>
                    <Column header="Country" body={countryBodyTemplate} sortable sortField="country.name" filter filterField="country.name" filterMatchMode="contains" filterPlaceholder="Search by country"></Column>
                    <Column header="Representative" body={representativeBodyTemplate} sortable sortField="representative.name" filter filterField="representative.name" filterMatchMode="in" filterElement={representativeFilter}></Column>
                    <Column field="status" header="Status" body={statusBodyTemplate} sortable filter filterMatchMode="equals" filterElement={statusFilter}></Column>
                </DataTable>
            </div>
        </div>
    );
}
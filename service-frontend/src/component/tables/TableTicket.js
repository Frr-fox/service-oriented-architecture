import {useDispatch, useSelector} from "react-redux";
import {useEffect, useRef, useState} from "react";
import {Button, Input, Row, Space, Table} from "antd";
import {SearchOutlined, SyncOutlined} from "@ant-design/icons";
import Highlighter from "react-highlight-words";
import api from "../../service/tripInstance";
import {addQueryParams} from "../../utils/ModalType";
import {needRefresh, setData} from "../../store/action/pageAction";
import {store} from "../../store/store";

const TableTicket = () => {
    const page = useSelector(store => store.page);
    const dispatch = useDispatch();

    const [searchText, setSearchText] = useState('');
    const [searchedColumn, setSearchedColumn] = useState('');
    const searchInput = useRef(null);
    const handleSearch = (selectedKeys, confirm, dataIndex) => {
        confirm();
        setSearchText(selectedKeys[0]);
        setSearchedColumn(dataIndex);
    };

    const handleReset = (clearFilters) => {
        clearFilters();
        setSearchText('');
    };

    const getColumnSearchProps = (dataIndex) => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters, close }) => (
            <div
                style={{
                    padding: 8,
                }}
                onKeyDown={(e) => e.stopPropagation()}
            >
                <Input
                    ref={searchInput}
                    placeholder={`Search ${dataIndex}`}
                    value={selectedKeys[0]}
                    onChange={(e) => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                    onPressEnter={() => handleSearch(selectedKeys, confirm, dataIndex)}
                    style={{
                        marginBottom: 8,
                        display: 'block',
                    }}
                />
                <Space>
                    <Button
                        type="primary"
                        onClick={() => handleSearch(selectedKeys, confirm, dataIndex)}
                        icon={<SearchOutlined />}
                        size="small"
                        style={{
                            width: 90,
                        }}
                    >
                        Search
                    </Button>
                    <Button
                        onClick={() => clearFilters && handleReset(clearFilters)}
                        size="small"
                        style={{
                            width: 90,
                        }}
                    >
                        Reset
                    </Button>
                    <Button
                        type="link"
                        size="small"
                        onClick={() => {
                            confirm({
                                closeDropdown: false,
                            });
                            setSearchText(selectedKeys[0]);
                            setSearchedColumn(dataIndex);
                        }}
                    >
                        Filter
                    </Button>
                    <Button
                        type="link"
                        size="small"
                        onClick={() => {
                            close();
                        }}
                    >
                        close
                    </Button>
                </Space>
            </div>
        ),
        filterIcon: (filtered) => (
            <SearchOutlined
                style={{
                    color: filtered ? '#1890ff' : undefined,
                }}
            />
        ),
        onFilter: (value, record) =>
            record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownOpenChange: (visible) => {
            if (visible) {
                setTimeout(() => searchInput.current?.select(), 100);
            }
        },
        render: (text) =>
            searchedColumn === dataIndex ? (
                <Highlighter
                    highlightStyle={{
                        backgroundColor: '#ffc069',
                        padding: 0,
                    }}
                    searchWords={[searchText]}
                    autoEscape
                    textToHighlight={text ? text.toString() : ''}
                />
            ) : (
                text
            ),
    });

    // const [data, setData] = useState();
    const [loading, setLoading] = useState(false);
    const [tableParams, setTableParams] = useState({
        pagination: {
            current: 1,
            pageSize: 3,
        },
    });

    const fetchData = () => {
        setLoading(true);
        api.get('/tickets')
            //todo: добавить параметры в запрос
            .then(res => {
                dispatch(setData(res.data.list));
                setLoading(false);
                setTableParams({
                    ...tableParams,
                    pagination: {
                        ...tableParams.pagination,
                    },
                });
            })
    };

    useEffect(() => {
        if (page.tab_number === 3) {
            dispatch(setData([]));
        }
    }, [page.tab_number]);

    useEffect( () => {
        if (page.need_refresh === true) {
            // fetchData();
            dispatch(needRefresh(false));
        }
    }, [])

    const handleTableChange = (pagination, filters, sorter) => {
        setTableParams({
            pagination,
            filters,
            ...sorter,
        });
    };

    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            sorter: (a, b) => a.id - b.id,
            width: '5%',
        },
        {
            title: 'Route ID',
            dataIndex: 'routeId',
            sorter: (a, b) => a.routeId - b.routeId,
            width: '10%',
            ...getColumnSearchProps('routeId'),
        },
        {
            title: 'Passenger',
            children: [
                {
                    title: 'ID',
                    dataIndex: 'passengerId',
                    sorter: (a, b) => a.passengerId - b.passengerId,
                    width: '10%',
                    ...getColumnSearchProps('passengerId'),
                },
                {
                    title: 'Name',
                    dataIndex: 'name',
                    sorter: true,
                    ...getColumnSearchProps('name'),
                    width: '12%',
                },
                {
                    title: 'Surname',
                    dataIndex: 'surname',
                    sorter: true,
                    ...getColumnSearchProps('surname'),
                    width: '12%',
                },
                {
                    title: 'Birth Date',
                    dataIndex: 'birthDate',
                    filters: [],
                    onFilter: (value, record) => record.birthDate.startsWith(value),
                    filterSearch: true,
                    sorter: true,
                    width: '10%',
                    ...getColumnSearchProps('birthDate'),
                },
            ]
        },
        {
            title: 'Departure Date',
            dataIndex: 'departureDate',
            filters: [],
            onFilter: (value, record) => record.buyDate.startsWith(value),
            filterSearch: true,
            sorter: true,
            width: '10%',
            ...getColumnSearchProps('departureDate'),
        },
        {
            title: 'Place',
            dataIndex: 'place',
            sorter: true,
            ...getColumnSearchProps('place'),
            width: '12%',
        },
        {
            title: 'Price',
            dataIndex: 'price',
            sorter: (a, b) => a.price - b.price,
            width: '7%',
            ...getColumnSearchProps('price'),
        },
    ];

    return (
        <>
            <Table
                columns={columns}
                dataSource={page.data}
                pagination={tableParams.pagination}
                loading={loading}
                onChange={handleTableChange}
                tableLayout='fixed'
                size='small'
            />
        </>

    )
}

export default TableTicket;
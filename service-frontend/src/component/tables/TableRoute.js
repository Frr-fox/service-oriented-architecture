import {Button, Input, Row, Space, Table} from "antd";
import {useEffect, useRef, useState} from "react";
import {SearchOutlined, SyncOutlined} from '@ant-design/icons';
import Highlighter from 'react-highlight-words';
import api from "../../service/axiosInstance";
import {addQueryParams} from "../../utils/ModalType";
import {useDispatch, useSelector} from "react-redux";
import {needRefresh, setData} from "../../store/action/pageAction";
import {store} from "../../store/store";

const TableRoute = () => {
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
        api.get('/routes' + addQueryParams({paramName: "page", paramValue: 1}, {paramName: "limit", paramValue: 100}))
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
        if (page.tab_number === 1) {
            fetchData();
        }
        if (page.tab_number === 2) {
            dispatch(setData([]))
        }
    }, [page.tab_number]);

    useEffect( () => {
        if (page.need_refresh !== false) {
            if (page.need_refresh !== 'partly') {
                fetchData();
            }
            dispatch(needRefresh(false));
        }
    }, [store.getState().page.need_refresh])
    
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
            title: 'Name',
            dataIndex: 'name',
            filters: [
                // {
                //     text: 'Category 1',
                //     value: 'Category 1',
                // },
            ],
            sorter: true,
            // filterMode: 'tree',
            // filterSearch: true,
            // onFilter: (value, record) => record.name.startsWith(value),
            ...getColumnSearchProps('name'),
            width: '12%',
        },
        {
            title: 'Coordinates',
            children: [
                {
                    title: 'X',
                    dataIndex: 'x',
                    sorter: (a, b) => a.x - b.x,
                    width: '5%',
                    ...getColumnSearchProps('x'),
                },
                {
                    title: 'Y',
                    dataIndex: 'y',
                    sorter: (a, b) => a.y - b.y,
                    width: '5%',
                    ...getColumnSearchProps('y'),
                }
            ],
        },
        {
            title: 'Creation Date',
            dataIndex: 'creationDate',
            filters: [],
            onFilter: (value, record) => record.creationDate.startsWith(value),
            filterSearch: true,
            sorter: true,
            width: '10%',
            ...getColumnSearchProps('creationDate'),
        },
        {
            title: 'From',
            children: [
                {
                    title: 'X',
                    dataIndex: 'fromX',
                    sorter: (a, b) => a.fromX - b.fromX,
                    width: '5%',
                    ...getColumnSearchProps('fromX'),
                },
                {
                    title: 'Y',
                    dataIndex: 'fromY',
                    sorter: (a, b) => a.fromY - b.fromY,
                    width: '5%',
                    ...getColumnSearchProps('fromY'),
                },
                {
                    title: 'Z',
                    dataIndex: 'fromZ',
                    sorter: (a, b) => a.fromZ - b.fromZ,
                    width: '5%',
                    ...getColumnSearchProps('fromZ'),
                }
            ],
            width: '50%',
        },
        {
            title: 'To',
            children: [
                {
                    title: 'Name',
                    dataIndex: 'toName',
                    width: '10%',
                    sorter: true,
                    ...getColumnSearchProps('toName'),
                },
                {
                    title: 'X',
                    dataIndex: 'toX',
                    sorter: (a, b) => a.toX - b.toX,
                    width: '5%',
                    ...getColumnSearchProps('toX'),
                },
                {
                    title: 'Y',
                    dataIndex: 'toY',
                    sorter: (a, b) => a.toY - b.toY,
                    width: '5%',
                    ...getColumnSearchProps('toY'),
                },
                {
                    title: 'Z',
                    dataIndex: 'toZ',
                    sorter: (a, b) => a.toZ - b.toZ,
                    width: '5%',
                    ...getColumnSearchProps('toZ'),
                },
            ]
        },
        {
            title: 'Distance',
            dataIndex: 'distance',
            sorter: (a, b) => a.distance - b.distance,
            width: '7%',
            ...getColumnSearchProps('distance'),
        },
    ];

    return (
        <>
            <Row align='end'>
                <Button type='text' icon={<SyncOutlined />} onClick={fetchData}>Refresh data</Button>
            </Row>
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

export default TableRoute;
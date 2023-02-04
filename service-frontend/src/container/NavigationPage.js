import {Button, Col, Layout, Row, Space} from "antd";
import FormRouteBetweenLocations from "../component/forms/FormRouteBetweenLocations";
import {FormRoute} from "../component/forms/FormRoute";
import {ModalType} from "../utils/ModalType";
import {
    setIsBetweenModal,
    setIsDateHidden,
    setIsIdHidden,
    setIsModalOpen,
    setModalType
} from "../store/action/pageAction";
import {useDispatch, useSelector} from "react-redux";
import {store} from "../store/store";
import TableRoute from "../component/tables/TableRoute";


const NavigationPage = () => {
    const page = useSelector(store => store.page);
    const dispatch = useDispatch();

    const handleBetween = () => {
        dispatch(setModalType(ModalType.BETWEEN));
        syncModalType();
    }

    const syncModalType = () => {
        switch (store.getState().page.modal_type) {
            case ModalType.ADD:
                dispatch(setIsModalOpen(true))
                dispatch(setIsIdHidden(true))
                dispatch(setIsDateHidden(true))
                dispatch(setIsBetweenModal(false))
                break;
            case ModalType.SHOW:
                dispatch(setIsModalOpen(true))
                dispatch(setIsIdHidden(false))
                dispatch(setIsDateHidden(false))
                dispatch(setIsBetweenModal(false))
                break;
            case ModalType.UPDATE:
                dispatch(setIsModalOpen(true))
                dispatch(setIsIdHidden(true))
                dispatch(setIsDateHidden(false))
                dispatch(setIsBetweenModal(false))
                break;
            case ModalType.BETWEEN:
                dispatch(setIsModalOpen(true))
                dispatch(setIsIdHidden(true))
                dispatch(setIsDateHidden(true))
                dispatch(setIsBetweenModal(true))
                break;
            default:
                dispatch(setIsModalOpen(false))
                break;
        }
    }

    return (
        <Layout className='page-content'>
            <Row>
                <Col span={11}>
                    <Row align='center' className='column-center'>
                        <FormRouteBetweenLocations/>
                    </Row>
                </Col>
                <Col span={12}>
                    <Row align='center'  className='column-center'>
                        <Space>
                            <p>Add route between specified locations</p>
                            <Button type='primary' onClick={handleBetween}>Add</Button>
                            <FormRoute/>
                        </Space>
                    </Row>
                </Col>
            </Row>
            <Row>
                <TableRoute/>
            </Row>
        </Layout>
    );
};

export default NavigationPage;
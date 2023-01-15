export const ModalType = {
    ADD: 'Add route',
    SHOW: 'Route',
    UPDATE: 'Update route',
    BETWEEN: 'Add route between specified locations'
};

//{paramName1: paramValue1}, {paramName2: paramValue2}, ...
export function addQueryParams() {
    if (arguments.length === 0)
        return "";
    let keys = "?";
    for (let i = 0; i < arguments.length; i++) {
        if (i > 0)
            keys += "&";
        keys += arguments[i].paramName + "=" + arguments[i].paramValue;
    }
    return keys;
}
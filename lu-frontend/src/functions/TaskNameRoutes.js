export function TaskNameRoutes(name) {
    var route = '';
    switch (name) {
        case 'UploadPDFForm':
        case 'uploadExtraMaterial':
            route = '/upload';
            break;
        case 'ReviewNewWriter':
            route = '/review';
            break;
        case 'Payment':
            route = '/pay';
            break;
        case 'reviewBookToPublish':
            route = '/reviewBookGeneral';
            break;
        case 'GiveExplanation':
        case 'GiveDeclineExplanation':
            route = '/giveExplanation';
            break;
        case 'UploadRestOfWork':
            route = '/uploadRestWork';
            break;
        case 'ComparePlagiats':
            route = '/comparePlagiats';
            break;
        case 'DownloadFile':
            route = '/downloadBook';
            break;
        case 'DecideSendingToBetaReaders':
            route = '/decideBeta';
            break;     
        default:
            route = '/';
            break;
    }
    return route;
};

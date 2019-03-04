/** 定义搜索控制器 */
app.controller("searchController", function ($scope, baseService) {
    $scope.searchParam={};
    $scope.search = function (){
        baseService.sendPost("/Search", $scope.searchParam)
            .then(function (response) {
                $scope.resultMap = response.data;
            })
    }
});

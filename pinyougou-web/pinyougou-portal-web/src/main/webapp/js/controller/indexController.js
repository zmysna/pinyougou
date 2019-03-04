/** 定义首页控制器层 */
app.controller("indexController", function($scope, baseService){
    $scope.findByContentCategoryId = function (id) {
        baseService.sendGet("/content/findByContentCategoryId?categoryId="+id)
            .then(function (response) {
                $scope.contentList = response.data;
            });

    }
});
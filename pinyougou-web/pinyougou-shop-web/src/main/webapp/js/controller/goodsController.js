/** 定义控制器层 */
app.controller('goodsController', function ($scope, $controller, baseService) {

    /** 指定继承baseController */
    $controller('baseController', {$scope: $scope});

    /** 添加或修改 */
    $scope.goods = {goodsDesc: {itemImages: [], specificationItems: []}, items: []};
    $scope.saveOrUpdate = function () {
        $scope.goods.goodsDesc.introduction = editor.html();
        var url = "insert";
        /*if ($scope.entity.id){
              url = "update";
          }*/
        /** 发送post请求 */
        baseService.sendPost("/goods/" + url, $scope.goods)
            .then(function (response) {
                if (response.data) {
                    /** 重新加载数据 */
                    $scope.goods = {};
                    editor.html('');
                } else {
                    alert("操作失败！");
                }
            });
    };


    /** 根据parentId查询所有的商品分类 */
    $scope.findByParentId = function (parentId, name) {
        baseService.sendGet("/itemCat/findAllByParentId", "parentId=" + parentId)
            .then(function (response) {
                if (response.data.length > 0) {
                    $scope[name] = response.data;
                } else {
                    alert("没有下级分类了");
                }
            });
    };
    /** 监听三个下拉列表，发出相应的请求*/
    $scope.$watch("goods.category1Id", function (newValue, oldValue) {
        if (newValue) {
            $scope.findByParentId(newValue, "itemCatList2")
        } else {
            $scope.itemCatList2 = [];
        }
    });
    $scope.$watch("goods.category2Id", function (newValue, oldValue) {
        if (newValue) {
            $scope.findByParentId(newValue, "itemCatList3")
        } else {
            $scope.itemCatList3 = [];
        }
    });
    $scope.$watch("goods.category3Id", function (newValue, oldValue) {
        if (newValue) {
            for (var i = 0; i < $scope.itemCatList3.length; i++) {
                var itemcat = $scope.itemCatList3[i];
                if (itemcat.id == newValue) {
                    $scope.goods.typeTemplateId = itemcat.typeId;
                    return;
                }
            }
        }
    });

    /** 监听分类模板ID改变品牌和扩展属性*/
    $scope.$watch("goods.typeTemplateId", function (newValue, oldValue) {
        if (newValue) {
            baseService.sendGet("/typeTemplate/findOne?id=" + newValue)
                .then(function (response) {
                    $scope.brandList = JSON.parse(response.data.brandIds);
                    $scope.goods.goodsDesc.customAttributeItems =
                        JSON.parse(response.data.customAttributeItems);
                });
            baseService.sendGet("/typeTemplate/findSpecList?id=" + newValue)
                .then(function (response) {
                    //[{"id":33,"text":"电视屏幕尺寸",options: []},{"id":43,"text":"内存大小",specificationOptions: []}]
                    $scope.specList = response.data;
                });
        }
    });


    /** 上传文件*/
    $scope.picEntity = {};
    $scope.uploadFile = function () {
        baseService.uploadFile().then(function (response) {
            if (response.data.status == 200) {
                $scope.picEntity.url = response.data.url;
            } else {
                alert("上传失败");
            }
        });
    }

    /** 图片列表添加一行 */
    $scope.addPic = function () {
        $scope.goods.goodsDesc.itemImages.push($scope.picEntity);
    };
    /** 图片列表移除指定行*/
    $scope.removePic = function (index, url) {
        $scope.goods.goodsDesc.itemImages.splice(index, 1);
        baseService.sendGet("/deleteFile", "url=" + url);
    }


    /*
        更新规格选项
    [{"attributeValue":["联通4G","移动4G","电信4G"],"attributeName":"网络"},
     {"attributeValue":["64G","128G"],"attributeName":"机身内存"}]
     */
    $scope.updateSpecAttr = function ($event, specName, optionName) {
        //查找是否有 attributeName 为 specName 的Json对象
        var obj = $scope.searchJsonByKey($scope.goods.goodsDesc.specificationItems, "attributeName", specName);
        // 如果存在则为JSON的attributeValue对象数组添加(或者删除)属性值
        if (obj) {
            if ($event.target.checked) {
                obj.attributeValue.push(optionName)
            } else {
                var index = obj.attributeValue.indexOf(optionName);
                obj.attributeValue.splice(index, 1);
                // 如果attributeValue的Json对象数组里面的值为空
                if (obj.attributeValue.length == 0) {
                    // 删除attributeName;
                    $scope.goods.goodsDesc.specificationItems
                        .splice($scope.goods.goodsDesc.specificationItems.indexOf(obj), 1);
                }
            }
        } else {
            // 否则创建一个JSON对象 attributeName 为 specName,  attributeValue 数组添加 optionName；
            $scope.goods.goodsDesc.specificationItems.push({"attributeName": specName, "attributeValue": [optionName]});
        }
    };

    // 通过指定的键值在JsonArr中查找是否有指定的Json对象
    $scope.searchJsonByKey = function (jsonArr, key, value) {
        for (var i = 0; i < jsonArr.length; i++) {
            if (jsonArr[i][key] == value) {
                return jsonArr[i];
            }
        }
    };


    /** 生成SKU列表*/
    /*
        goods.goodsDesc.specificationItems
        [{"attributeValue":["联通4G","移动4G","电信4G"],"attributeName":"网络"},
        {"attributeValue":["64G","128G"],"attributeName":"机身内存"}]
        {"网络":"联通4G","机身内存":"64G"}
    */
    $scope.createItems = function () {
        var specItems = $scope.goods.goodsDesc.specificationItems;
        /** 定义SKU数组变量，并初始化*/
        $scope.goods.items = [{spec: {}, price: 0, num: 9999, status: 0, isDefault: 0}]
        for (var i = 0; i < specItems.length; i++) {
            var specItem = specItems[i];
            var specName = specItem.attributeName;
            var newItems = new Array();
            for (var k = 0; k < $scope.goods.items.length; k++) {
                var item = $scope.goods.items[k];
                for (var j = 0; j < specItem.attributeValue.length; j++) {
                    var newItem = JSON.parse(JSON.stringify(item));
                    newItem.spec[specName] = specItem.attributeValue[j];
                    newItems.push(newItem);
                }
            }
            $scope.goods.items = newItems;
        }
    };

    /** 多条件分页查询商品*/
    $scope.searchEntity = {};
    $scope.search = function (pageNum, pageSize) {
        baseService.findByPage("/goods/findByPage", pageNum, pageSize, $scope.searchEntity)
            .then(function (response) {
                $scope.dataList = response.data.list;
                $scope.paginationConf.totalItems = response.data.totalNum;
            }, function (response) {
                alert("查询失败");
            });
    }

    $scope.status = ['未审核', '已审核', '审核未通过', '关闭'];


    /** 批量删除商品(修改删除状态) */
    $scope.delete = function () {
        if ($scope.ids.length > 0) {
            baseService.deleteById("/goods/delete", $scope.ids)
                .then(function (response) {
                    /** 重新加载数据 */
                    $scope.reload();
                    /** 清空ids数组 */
                    $scope.ids = [];
                }, function (response) {
                    alert("删除失败！");
                });
        } else {
            alert("请选择要删除的商品！");
        }
    };

    /** 商品上下架*/
    $scope.updateMarktable = function (status) {
        baseService.sendGet("/goods/updateMarktable?ids=" + $scope.ids + "&status=" + status)
            .then(function (response) {
                alert("操作成功")
                /** 重新加载数据 */
                $scope.reload();
                /** 清空ids数组 */
                $scope.ids = [];
            }, function (response) {
                alert("操作失败！");
            });
    }

});
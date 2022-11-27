window.onload = function () {
            var prev = document.getElementById('prev');
            var next = document.getElementById('next');

            // 导航栏居中显示
            var imgDiv = document.getElementById('navDiv');
            var outer = document.getElementById('outer');
            var imgs = document.getElementsByTagName('img');
            // imgDiv.style.left = (outer.offsetWidth - imgDiv.offsetWidth) / 2 + '%';

            var index = 0;
            var allA = document.getElementsByClassName('aa');
            allA[index].style.backgroundColor = 'rgb(255, 255, 255)';

            // 图片轮播

            var img = document.getElementsByTagName('img')[0];
            // 这里！路径要改好！
            var Array = ['http://43.142.110.195:9999/images/1.jpg', 'http://43.142.110.195:9999/images/2.jpg', 'http://43.142.110.195:9999/images/3.jpg', 'http://43.142.110.195:9999/images/4.jpg', 'http://43.142.110.195:9999/images/5.jpg'];

            var index = 0;

            prev.onclick = function () {
                index--;

                if (index < 0) {
                    // index = 0;
                    index = Array.length - 1;
                    allA[index].style.backgroundColor = 'rgb(255, 255, 255)';
                }
                img.src = Array[index];
                setA();
            };

            next.onclick = function () {
                index++;
                if (index > Array.length - 1) {
                    // index = Array.length - 1;
                    index = 0;
                    allA[index].style.backgroundColor = 'rgb(255, 255, 255)';
                }
                img.src = Array[index];
                setA();
            };



            // 为所有超链接绑定鼠标事件
            for (var i = 0; i < allA.length; i++) {
                allA[i].num = i;

                allA[i].onclick = function () {
                    index = this.num;

                    // 改变图片
                    img.src = Array[index];

                    // 修改正在显示的a
                    allA[index].style.backgroundColor = 'rgb(255, 255, 255)';
                    setA();
                };

            };
            // 创建一个方法来设置选中的a
            function setA() {
                for (var i = 0; i < allA.length; i++) {
                    allA[i].style.backgroundColor = "";
                }
                allA[index].style.backgroundColor = 'rgb(255, 255, 255)';
            }
        };

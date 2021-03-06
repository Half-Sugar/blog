/*
 * Bolo - A stable and beautiful blogging system based in Solo.
 * Copyright (c) 2020, https://github.com/adlered
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/**
 * @fileoverview util and every page should be used.
 *
 * @author <a href="http://vanessa.b3log.org">Liyuan Li</a>
 * @author <a href="http://88250.b3log.org">Liang Ding (Solo Author)</a>
 * @author <a href="https://github.com/adlered">adlered (Bolo Author)</a>
 */

/**
 * @description Util
 * @static
 */
var Util = {
    isArticlePage: function (href) {
        var isArticle = true
        if (href.indexOf(Label.servePath + '/tags/') > -1) {
            isArticle = false
        }
        if (href.indexOf(Label.servePath + '/tags.html') > -1) {
            isArticle = false
        }
        if (href.indexOf(Label.servePath + '/category/') > -1) {
            isArticle = false
        }
        if (href.indexOf(Label.servePath + '/archives.html') > -1) {
            isArticle = false
        }
        if (href.indexOf(Label.servePath + '/archives/') > -1) {
            isArticle = false
        }
        if (href.indexOf(Label.servePath + '/links.html') > -1) {
            isArticle = false
        }
        if (href === Label.servePath) {
            isArticle = false
        }
        if (/^[0-9]*$/.test(href.replace(Label.servePath + '/', ''))) {
            isArticle = false
        }
        return isArticle
    },
    /**
     * ????????? Pjax
     * @param cb ???????????????????????????????????????
     */
    initPjax: function (cb) {
        if ($('#pjax').length === 1) {
            $.pjax({
                selector: 'a',
                container: '#pjax',
                show: '',
                cache: false,
                storage: true,
                titleSuffix: '',
                filter: function (href, element) {
                    if (!href) {
                        return true
                    }
                    if (element.getAttribute('target') === '_blank') {
                        return true
                    }
                    if (href === Label.servePath + '/rss.xml' ||
                        href.indexOf(Label.servePath + '/admin-index.do') > -1) {
                        return true
                    }
                    // ??????
                    if (href.indexOf('#') === 0) {
                        return true
                    }
                    // ???????????????
                    if (element.href.indexOf(Label.servePath) > -1) {
                        return false
                    }
                    return true
                },
                callback: function () {
                    Util.parseMarkdown()
                    Util.parseLanguage()
                    cb && cb()
                },
            })
            NProgress.configure({showSpinner: false})
            $('#pjax').bind('pjax.start', function () {
                NProgress.start()
            })
            $('#pjax').bind('pjax.end', function () {
                window.scroll(window.scrollX, 0)
                NProgress.done()
            })
        }
    },
    /**
     * ????????????
     */
    previewImg: function () {
        $('body').on('click', '.vditor-reset img', function () {
            if ($(this).hasClass('prevent')) {
                return
            }
            window.open(this.src)
        })
    },
    /**
     * ???????????? css
     * @param url css ??????????????????
     * @param id css ????????????
     */
    addStyle: function (url, id) {
        if (!document.getElementById(id)) {
            var styleElement = document.createElement('link')
            styleElement.id = id
            styleElement.setAttribute('rel', 'stylesheet')
            styleElement.setAttribute('type', 'text/css')
            styleElement.setAttribute('href', url)
            document.getElementsByTagName('head')[0].appendChild(styleElement)
        }
    },
    /**
     * ???????????? js
     * @param url js ??????????????????
     * @param id js ????????????
     */
    addScript: function (url, id) {
        if (!document.getElementById(id)) {
            var xhrObj = new XMLHttpRequest()
            xhrObj.open('GET', url, false)
            xhrObj.setRequestHeader('Accept',
                'text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01')
            xhrObj.send('')
            var scriptElement = document.createElement('script')
            scriptElement.id = id
            scriptElement.type = 'text/javascript'
            scriptElement.text = xhrObj.responseText
            document.getElementsByTagName('head')[0].appendChild(scriptElement)
        }
    },
    /*
    * @description ??????????????????
    */
    parseLanguage: function () {
        Vditor.highlightRender({
            style: Label.hljsStyle,
            enable: !Label.luteAvailable,
        }, document)
    },
    /**
     * ????????????????????????????????????????????????????????????????????????????????????
     * @returns {undefined}
     */
    parseMarkdown: function () {

        if (typeof Vditor === 'undefined') {
            Util.addScript(
                'https://cdn.jsdelivr.net/npm/vditor@3.4.1/dist/method.min.js',
                'vditorPreviewScript')
        }

        Vditor.highlightRender({
            enable: !Label.luteAvailable,
            lineNumber: Label.showCodeBlockLn,
            style: Label.hljsStyle,
        }, document);

        Vditor.codeRender(document.body, Label.langLabel);
        Vditor.graphvizRender(document.body);
        Vditor.mathRender(document.body);
        Vditor.abcRender();
        Vditor.chartRender();
        Vditor.mindmapRender();
        Vditor.mediaRender(document.body);
        Vditor.mermaidRender(document.body);
    },
    /**
     * @description IE6/7???????????? kill-browser ??????
     */
    killIE: function (ieVersion) {
        var addKillPanel = function () {
            if (Cookie.readCookie('showKill') === '') {
                try {
                    var left = ($(window).width() - 781) / 2,
                        top1 = ($(window).height() - 680) / 2
                    var killIEHTML = '<div style=\'display: block; height: 100%; width: 100%; position: fixed; background-color: rgb(0, 0, 0); opacity: 0.6;filter: alpha(opacity=60); top: 0px;z-index:110\'></div>'
                        + '<iframe style=\'left:' + left + 'px;z-index:120;top: ' + top1 +
                        'px; position: fixed; border: 0px none; width: 781px; height: 680px;\' src=\'' +
                        Label.servePath + '/kill-browser\'></iframe>'
                    $('body').append(killIEHTML)
                } catch (e) {
                    var left = 10,
                        top1 = 0
                    var killIEHTML = '<div style=\'display: block; height: 100%; width: 100%; position: fixed; background-color: rgb(0, 0, 0); opacity: 0.6;filter: alpha(opacity=60); top: 0px;z-index:110\'></div>'
                        + '<iframe style=\'left:' + left + 'px;z-index:120;top: ' + top1 +
                        'px; position: fixed; border: 0px none; width: 781px; height: 680px;\' src=\'' +
                        Label.servePath + '/kill-browser\'></iframe>'
                    document.body.innerHTML = document.body.innerHTML + killIEHTML
                }
            }
        }

        var ua = navigator.userAgent.split('MSIE')[1]
        if (ua) {
            if (!ieVersion) {
                ieVersion = 7
            }
            if (parseFloat(ua.split(';')) <= ieVersion) {
                addKillPanel()
            }
        }
    },
    /**
     * @description ??????????????????
     * @param {String} skin ????????????????????????
     */
    switchMobile: function (skin) {
        Cookie.createCookie('btouch_switch_toggle', skin, 365)
        setTimeout(function () {
            location.reload()
        }, 1250)
    },
    /**
     * @description topbar ????????????
     */
    setTopBar: function () {
        var $top = $('#top')
        if ($top.length === 1) {
            var $showTop = $('#showTop')
            $showTop.click(function () {
                $top.slideDown()
                $showTop.hide()
            })
            $('#hideTop').click(function () {
                $top.slideUp()
                $showTop.show()
            })
        }
    },
    /**
     * @description ????????????
     */
    goTop: function () {
        $('html, body').animate({scrollTop: 0}, 800)
    },
    /**
     * @description ????????????
     */
    goBottom: function (bottom) {
        if (!bottom) {
            bottom = 0
        }
        $('html, body').
        animate({scrollTop: $(document).height() - $(window).height() - bottom},
            800)
    },
    /**
     * @description ??????????????????????????????
     */
    init: function () {
        Util.killIE()
        Util.parseMarkdown()
        Util.parseLanguage()
        Util.initSW()
        Util.previewImg()
        Util.initDebugInfo()
    },
    /**
     * ??????????????????
     */
    initDebugInfo: function () {
        /* console.log(
          '%cBolo%c\n  ????????????????Solo??????????????????????????????????????????' + Label.version + ' ?? ' +
          (new Date).getFullYear(),
          'font-size:96px;color:#3b3e43', 'font-size:12px;color:rgba(0,0,0,0.38);') */
    },
    /**
     * @description ?????? Service Work
     */
    initSW: function () {
        if (navigator.serviceWorker) {
            navigator.serviceWorker.register('/sw.js', {scope: '/'})
        }
    },
    /**
     * @description ??????????????????????????????
     * @param {Dom} comments ??????????????????
     */
    replaceSideEm: function (comments) {
        for (var i = 0; i < comments.length; i++) {
            var $comment = $(comments[i])
            $comment.html($comment.html())
        }
    },
    /**
     * @description ?????? tags??????????????????
     * @param {String} [id] tags ????????? id???????????? tags
     */
    buildTags: function (id) {
        id = id || 'tags'
        // ????????????????????????????????????????????????
        var classes = ['tags1', 'tags2', 'tags3', 'tags4', 'tags5'],
            bList = $('#' + id + ' b').get()
        var max = parseInt($('#' + id + ' b').last().text())
        var distance = Math.ceil(max / classes.length)
        for (var i = 0; i < bList.length; i++) {
            var num = parseInt(bList[i].innerHTML)
            // ???????????? tag ?????????????????????????????? class
            for (var j = 0; j < classes.length; j++) {
                if (num > j * distance && num <= (j + 1) * distance) {
                    bList[i].parentNode.className = classes[j]
                    break
                }
            }
        }

        // ???????????????????????????????????????
        $('#' + id).html($('#' + id + ' li').get().sort(function (a, b) {
            var valA = $(a).find('span').text().toLowerCase()
            var valB = $(b).find('span').text().toLowerCase()
            // ???????????????????????????
            return valA.localeCompare(valB)
        }))
    },
    /**
     * @description ??????????????????????????????
     * @param {String} time ??????
     * @param {String} format ????????????????????????
     * @returns {String} ?????????????????????
     */
    toDate: function (time, format) {
        var dateTime = new Date(time)
        var o = {
            'M+': dateTime.getMonth() + 1, //month
            'd+': dateTime.getDate(), //day
            'H+': dateTime.getHours(), //hour
            'm+': dateTime.getMinutes(), //minute
            's+': dateTime.getSeconds(), //second
            'q+': Math.floor((dateTime.getMonth() + 3) / 3), //quarter
            'S': dateTime.getMilliseconds(), //millisecond
        }

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1,
                (dateTime.getFullYear() + '').substr(4 - RegExp.$1.length))
        }

        for (var k in o) {
            if (new RegExp('(' + k + ')').test(format)) {
                format = format.replace(RegExp.$1,
                    RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(
                        ('' + o[k]).length))
            }
        }
        return format
    },
}
if (!Cookie) {
    /**
     * @description Cookie ????????????
     * @static
     */
    var Cookie = {
        /**
         * @description ?????? cookie
         * @param {String} name cookie key
         * @returns {String} ?????? key ???????????? key ?????????????????? ""
         */
        readCookie: function (name) {
            var nameEQ = name + '='
            var ca = document.cookie.split(';')
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i]
                while (c.charAt(0) == ' ')
                    c = c.substring(1, c.length)
                if (c.indexOf(nameEQ) == 0)
                    return decodeURIComponent(c.substring(nameEQ.length, c.length))
            }
            return ''
        },
        /**
         * @description ?????? Cookie
         * @param {String} name ?????? key ??? name ????????? Cookie
         */
        eraseCookie: function (name) {
            this.createCookie(name, '', -1)
        },
        /**
         * @description ?????? Cookie
         * @param {String} name ?????? Cookie ????????? key
         * @param {String} value ?????? Cookie ????????????????????? UTF-8 ??????
         * @param {Int} days Cookie ????????????
         */
        createCookie: function (name, value, days) {
            var expires = ''
            if (days) {
                var date = new Date()
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000))
                expires = '; expires=' + date.toGMTString()
            }
            document.cookie = name + '=' + encodeURIComponent(value) + expires +
                '; path=/'
        },
    }
}
jQuery(document).ready(function($) {
    // check if background-images have been loaded and show list items
    $('.projects-container li').bgLoaded({
        afterLoaded: function() {
            showCaption($('.projects-container li').eq(0));
        }
    });

    // open project
    $('.projects-container li').on('click', function() {
        var selectedProject = $(this),
                toggle = !selectedProject.hasClass('is-full-width');
        if (toggle)
            toggleProject($(this), $('.projects-container'), toggle);
    });

    // close project
    $('.projects-container .cd-close').on('click', function() {
        toggleProject($('.is-full-width'), $('.projects-container'), false);
    });

    // scroll to project info
    $('.projects-container .cd-scroll').on('click', function() {
        $('body,html').animate({'scrollTop': $(window).height()}, 500);
    });

    // update title and .cd-scroll opacity while scrolling
    $(window).on('scroll', function() {
        window.requestAnimationFrame(changeOpacity);
    });

    function toggleProject(project, container, bool) {
        if (bool) {
            // expand project
            container.addClass('project-is-open');
            project.addClass('is-full-width').siblings('li').removeClass('is-loaded');
        } else {
            //check media query
            var mq = window.getComputedStyle(document.querySelector('.projects-container'), '::before').getPropertyValue('content'),
                    delay = (mq == 'mobile') ? 100 : 0;

            container.removeClass('project-is-open');
            // fade out project
            project.animate({opacity: 0}, 800, function() {
                project.removeClass('is-loaded');
                $('.projects-container').find('.cd-scroll').attr('style', '');
                setTimeout(function() {
                    project.attr('style', '').removeClass('is-full-width').find('.cd-title').attr('style', '');
                }, delay);
                setTimeout(function() {
                    showCaption($('.projects-container li').eq(0));
                }, 300);
            });
        }
    }

    function changeOpacity() {
        var newOpacity = 1 - $(window).scrollTop() / 300;
        $('.projects-container .cd-scroll').css('opacity', newOpacity);
        $('.is-full-width .cd-title').css('opacity', newOpacity);
    }

    function showCaption(project) {
        if (project.length > 0) {
            setTimeout(function() {
                project.addClass('is-loaded');
                showCaption(project.next());
            }, 150);
        }
    }
});

 /* BG Loaded */
 /* Copyright (c) 2014 Jonathan Catmull */
 /* Licensed under the MIT license. */
(function($) {
    $.fn.bgLoaded = function(custom) {
        var self = this;

        // Default plugin settings
        var defaults = {
            afterLoaded: function() {
                this.addClass('bg-loaded');
            }
        };

        // Merge default and user settings
        var settings = $.extend({}, defaults, custom);

        // Loop through element
        self.each(function() {
            var $this = $(this),
                    bgImgs = window.getComputedStyle($this.get(0), '::before').getPropertyValue('content').replace(/'/g, "").replace(/"/g, "").split(', ');
            $this.data('loaded-count', 0);
            $.each(bgImgs, function(key, value) {
                var img = value.replace(/^url\(["']?/, '').replace(/["']?\)$/, '');
                $('<img/>').attr('src', img).load(function() {
                    $(this).remove(); // prevent memory leaks
                    $this.data('loaded-count', $this.data('loaded-count') + 1);
                    if ($this.data('loaded-count') >= bgImgs.length) {
                        settings.afterLoaded.call($this);
                    }
                });
            });

        });
    };
})(jQuery);
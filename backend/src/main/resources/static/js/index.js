const NUMBER_FORMAT = new Intl.NumberFormat('zh-CN');
const PERCENTAGE_FORMAT = new Intl.NumberFormat('zh-CN', {style: 'percent'});

function formatNumber(num) {
  return NUMBER_FORMAT.format(num);
}

function formatPercentage(num) {
  let s = PERCENTAGE_FORMAT.format(num);
  return s.length > 2 ? s : '\xA0' + s + '\xA0';
}

function buildUrl(path, src, dest) {
  let url = new URL(path, window.location.href);
  url.searchParams.append('source', src);
  url.searchParams.append('destination', dest);
  return url;
}

jQuery(function ($) {
  function show() {
    let s = $('#source').val();
    let t = $('#destination').val();

    countAll(s, t);
    findTop(s, t);
    findBottom(s, t);
    countByHour(s, t);
    countByAirlines(s, t);
  }

  function countAll(src, dest) {
    let url = buildUrl('countAll', src, dest)
    fetch(url, {
      mode: 'no-cors',
      headers: {
        Accept: 'application/json'
      }
    })
      .then((response) => response.json())
      .then((data) => {
        $('#total').text(formatNumber(data.total));
        $('#otp').text(formatPercentage(data.otp));
        $('#ontimes').text(formatNumber(data.ontimes));
        $('#rod').text(formatPercentage(data.rod));
        $('#delays').text(formatNumber(data.delays));
        $('#roc').text(formatPercentage(data.roc));
        $('#cancellations').text(formatNumber(data.cancellations));
      });
  }

  function countByAirlines(src, dest) {

  }

  function countByHour(src, dest) {

  }

  function findTop(src, dest) {

  }

  function findBottom(src, dest) {

  }

  $('#swap').click(
    function () {
      let s = $('#source').val();
      let t = $('#destination').val();
      $('#source').val(t);
      $('#destination').val(s);
    }
  );

  $('#start').click(
    function () {
      show();
    }
  );

  // typeahead.js
  // example taken from plugin's page at: https://twitter.github.io/typeahead.js/examples/
  const substringMatcher = function (strs) {
    return function findMatches(q, cb) {
      var matches, substringRegex;

      // an array that will be populated with substring matches
      matches = [];

      // regex used to determine if a string contains the substring `q`
      substrRegex = new RegExp(q, 'i');

      // iterate through the pool of strings and for any string that
      // contains the substring `q`, add it to the `matches` array
      $.each(strs, function (i, str) {
        if (substrRegex.test(str)) {
          // the typeahead jQuery plugin expects suggestions to a
          // JavaScript object, refer to typeahead docs for more info
          matches.push({
            value: str
          });
        }
      });

      cb(matches);
    }
  };

  show();

  fetch('cities', {
    mode: 'no-cors',
    headers: {
      Accept: 'application/json'
    }
  })
    .then((response) => response.json())
    .then((data) => {
      $('input.typeahead').typeahead({
        hint: true,
        highlight: true,
        minLength: 1,
        classNames: {
          menu: 'dropdown-menu',
          suggestion: 'dropdown-item',
          cursor: 'bgc-yellow-m2'
        }
      }, {
        name: 'states',
        displayKey: 'value',
        source: substringMatcher(data),
        limit: 10
      })
    });

  // instead of listening to window `scroll` event, we use `IntersectionObserver`
  // to observe an element and determine when it becomes visible/invisible during scrolling

  // when we scroll down, navbar becomes fixed
  // we use a hidden element (#scroll-down) to determine when to do the above

  if (window.IntersectionObserver) {
    var observer = new window.IntersectionObserver(function (entries) {
      var entry = entries[0];
      var isOut = entry.intersectionRatio < 1 && entry.boundingClientRect.y < 0
      if (isOut) {
        $('.navbar').addClass('navbar-compact')
      } else {
        $('.navbar').removeClass('navbar-compact')
      }
    }, {
      threshold: [1.0],
      delay: 100
    })

    observer.observe(document.getElementById('scroll-down'))
  }
})

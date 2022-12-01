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

const chartByHours = echarts.init(document.getElementById('chartByHours'), 'macarons', {renderer: 'svg'});
const chartByAirlines = echarts.init(document.getElementById('chartByAirlines'), 'macarons', {renderer: 'svg'});

const option = {
  legend: {
    data: ['准点航班数', '延误航班数', '取消航班数', '准点率']
  },
  tooltip: {
    trigger: 'axis',
  },
  xAxis: {
    type: 'category',
    axisLabel: {fontSize: '0.9em'},
    data: []
  },
  yAxis: [
    {
      type: 'value',
      name: '航班数',
      nameTextStyle: {fontSize: '0.9em'},
      axisLabel: {
        axisLabel: {fontSize: '0.9em'},
      }
    },
    {
      type: 'value',
      name: '准点率',
      nameTextStyle: {fontSize: '0.9em'},
      splitLine: {show: false},
      splitArea: {show: false},
      axisLabel: {
        axisLabel: {fontSize: '0.9em'},
        formatter: '{value} %'
      }
    }
  ],
  series: [
    {
      name: '准点率',
      data: [],
      type: 'line',
      yAxisIndex: 1,
      tooltip: {
        valueFormatter: function (value) {
          return value + '%';
        }
      },
      markPoint: {
        data: [
          {type: 'max', name: '最优'},
          {type: 'min', name: '最差'}
        ]
      },
    },
    {
      name: '准点航班数',
      type: 'bar',
      stack: 'total',
      tooltip: {
        valueFormatter: function (value) {
          return value;
        }
      },
      data: []
    },
    {
      name: '延误航班数',
      type: 'bar',
      stack: 'total',
      tooltip: {
        valueFormatter: function (value) {
          return value;
        }
      },
      data: []
    },
    {
      name: '取消航班数',
      type: 'bar',
      stack: 'total',
      tooltip: {
        valueFormatter: function (value) {
          return value;
        }
      },
      data: []
    },
  ]
};
chartByHours.setOption(option);
chartByAirlines.setOption(option);

jQuery(function ($) {
  function show() {
    let s = $('#source').val();
    let t = $('#destination').val();

    countAll(s, t);
    countByHour(s, t);
    countByAirlines(s, t);
    findTop(s, t);
    findBottom(s, t);
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
        // $('#total').text(formatNumber(data.total));
        const countUpTotal = new countUp.CountUp('total', data.total);
        if (!countUpTotal.error) {
          countUpTotal.start();
        } else {
          console.error(countUpTotal.error);
        }
        $('#otp').text(formatPercentage(data.otp));
        $('#ontimes').text(formatNumber(data.ontimes));
        $('#rod').text(formatPercentage(data.rod));
        $('#delays').text(formatNumber(data.delays));
        $('#roc').text(formatPercentage(data.roc));
        $('#cancellations').text(formatNumber(data.cancellations));
      });
  }

  function countByAirlines(src, dest) {
    let url = buildUrl('countByAirlines', src, dest)
    fetch(url, {
      mode: 'no-cors',
      headers: {
        Accept: 'application/json'
      }
    })
      .then((response) => response.json())
      .then((data) => fillChartDate(data, chartByAirlines));
  }

  function countByHour(src, dest) {
    let url = buildUrl('countByHour', src, dest)
    fetch(url, {
      mode: 'no-cors',
      headers: {
        Accept: 'application/json'
      }
    })
      .then((response) => response.json())
      .then((data) => fillChartDate(data, chartByHours));
  }

  function fillChartDate(data, chartObj) {
    let category = [];
    let otps = [];
    let ontimes = [];
    let delays = [];
    let cancellations = [];

    data.forEach(e => {
      category.push(e.category);
      ontimes.push(e.ontimes);
      delays.push(e.delays);
      cancellations.push(e.cancellations);
      otps.push((e.otp * 100).toFixed(0));
    });

    let optionExt = {
      xAxis: {
        data: category
      },
      series: [
        {
          name: '准点率',
          data: otps,
        },
        {
          name: '准点航班数',
          data: ontimes
        },
        {
          name: '延误航班数',
          data: delays
        },
        {
          name: '取消航班数',
          data: cancellations
        },
      ]
    };

    chartObj.setOption(optionExt);
  }

  function findTop(src, dest) {
    let url = buildUrl('findTop', src, dest)
    fetch(url, {
      mode: 'no-cors',
      headers: {
        Accept: 'application/json'
      }
    })
      .then((response) => response.json())
      .then((data) => {
        let l = data.length;
        let tbody = $('#top-table > tbody');
        fillTable(tbody, l, data);
      });
  }

  function findBottom(src, dest) {
    let url = buildUrl('findBottom', src, dest)
    fetch(url, {
      mode: 'no-cors',
      headers: {
        Accept: 'application/json'
      }
    })
      .then((response) => response.json())
      .then((data) => {
        let l = data.length;
        let tbody = $('#bottom-table > tbody');
        fillTable(tbody, l, data);
      });
  }

  function fillTable(tbody, l, data) {
    for (let i = 1; i < 6; i++) {
      let th = tbody.children('tr:nth-child(' + i + ')');
      if (i <= l) {
        let item = data[i - 1];
        th.children('td:nth-child(1)').children('div').text(item.category);
        th.children('td:nth-child(2)').text(formatNumber(item.total));
        th.children('td:nth-child(3)').text(formatNumber(item.ontimes));
        th.children('td:nth-child(4)').text(formatNumber(item.delays));
        th.children('td:nth-child(5)').text(formatNumber(item.cancellations));
        th.children('td:nth-child(6)').text(formatPercentage(item.otp));
        th.children('td:nth-child(7)').text(formatPercentage(item.rod));
        th.children('td:nth-child(8)').text(formatPercentage(item.roc));
      } else {
        th.children('td:nth-child(1)').children('div').text('');
        th.children('td:nth-child(2)').text('');
        th.children('td:nth-child(3)').text('');
        th.children('td:nth-child(4)').text('');
        th.children('td:nth-child(5)').text('');
        th.children('td:nth-child(6)').text('');
        th.children('td:nth-child(7)').text('');
        th.children('td:nth-child(8)').text('');
      }
    }
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
